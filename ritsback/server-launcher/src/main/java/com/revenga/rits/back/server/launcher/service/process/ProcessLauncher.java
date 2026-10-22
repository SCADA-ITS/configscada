package com.revenga.rits.back.server.launcher.service.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

import com.revenga.rits.back.adapter.lib.model.ServerLauncherAdapterV1;
import com.revenga.rits.back.adapter.lib.model.ServerLauncherAdapterV1.Processes.Proc;
import com.revenga.rits.back.adapter.lib.model.ServerLauncherAdapterV1.Processes.Proc.Thresholds;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.service.impl.DataCache;
import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.ProcessInfo;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.entities.lib.repository.ProcessInfoRepository;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class ProcessLauncher implements Runnable {

	List<Proc> processes;

	// Map <Id, Pair<Pid, Timestamp>>
	Map<Short, Pair<Long, Long>> runningProcesses;
	short checkAliveSecs;
	short traceStatsSecs;
	DataCache dataCache;

	boolean stop;
	boolean alive;
	private final Object lock = new Object();
	private static final String GREATER_THAN = ">";
	private static final String LESS_THAN = "<";
	private static final Integer FIELD = 0;
	private static final Integer OPERATION = 1;
	private static final Integer VALUE = 2;

	private ProcessInfoRepository processInfoRepository;

	public ProcessLauncher(ServerLauncherAdapterV1 serverLauncherAdapterV1) throws ProcessLauncherException {

		super();

		this.stop = false;
		this.alive = false;
		dataCache = new DataCache(RedisConnection.getInstance());
		processInfoRepository = new ProcessInfoRepository();

		if (serverLauncherAdapterV1.getProcesses() != null
				&& !CollectionUtils.isEmpty(serverLauncherAdapterV1.getProcesses().getProc())) {

			// this.processes = serverLauncherAdapterV1.getProcesses().getProc();
			this.processes = serverLauncherAdapterV1.getProcesses().getProc().stream().filter(e -> e.isEnabled())
					.collect(Collectors.toList());

		} else {

			throw new ProcessLauncherException("No process in xml file");
		}

		this.checkAliveSecs = serverLauncherAdapterV1.getGeneral().getCheckAliveS();
		this.traceStatsSecs = serverLauncherAdapterV1.getGeneral().getTraceStatsS();

		this.runningProcesses = new HashMap<>();
	}

	@Override
	public void run() {

		long timeElapsed = 0;
		this.alive = true;

		log.info(Thread.currentThread().getName() + " is running...");

		stop = !loadProcesses();
		
		synchronized (lock) {
			
			while (!stop) {

				try {
					
					long initTime = Instant.now().toEpochMilli();

					for (Map.Entry<Short, Pair<Long, Long>> entry : runningProcesses.entrySet()) {

						if (stop) {

							break;
						}

						Short id = entry.getKey();
						Pair<Long, Long> pair = entry.getValue();
						Proc process = processes.stream().filter(e -> e.getId() == id).findFirst().orElse(null);

						if (process != null) {

							ProcessInfo processInfo = processInfoRepository.getByPid(pair.getLeft(), false);

							if (!isStillAlive(String.valueOf(pair.getLeft()))) {

								String message = "Process " + pair.getLeft() + " running since "
										+ Instant.ofEpochMilli(pair.getRight()).toString() + " (" + process.getName()
										+ ") is down...";

								AuditHelper.Audit(LogType.LOG_TYPE_PROCESSES, LogSubtype.LOG_SUBTYPE_PROCESS_DOWN,
										User.USER_SYSTEM, LogLevel.ERROR, process.getName(),
										Instant.ofEpochMilli(pair.getRight()).toString());

								log.error(message);

								if (processInfo != null && processInfo.getNumRestarts() != null) {

									processInfo.setNumRestarts(processInfo.getNumRestarts() + 1);
									dataCache.put(processInfo);
								}

								loadProcess(id);
							} else {

								// check thresholds

								if (isOutOfThresholds(process.getThresholds(), processInfo, process)) {

									unloadByPid(String.valueOf(pair.getLeft()), process);
								}
							}
						}
					}
					
					long lastProcessedTime = Instant.now().toEpochMilli();;

					if (stop) {

						break;
					}
					else {
						
						long waitTime = checkAliveSecs * 1000 - (lastProcessedTime - initTime);
						
						if (waitTime > 0) {
							
							lock.wait(waitTime);
						}
						
						Instant timeToKeepAlive = Instant.now();
						
						timeElapsed += Duration.between(timeToKeepAlive, Instant.now()).toMillis();

						if (timeElapsed >= (long) this.traceStatsSecs * 1000) {

							traceProcessesStats();

							timeElapsed = 0L;
						}
					}

				} catch (InterruptedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
				}
			}
		}

		unload();

		log.info(Thread.currentThread().getName() + " is stopped");

		this.alive = false;
	}

	public void killByPid(Long pid) {

		if (pid != null) {

			Proc process = getProc(pid);

			String os = System.getProperty("os.name").toLowerCase();

			try {

				Runtime rt = Runtime.getRuntime();

				log.info("[Pid = " + pid + "]" + (process != null ? (" Kill " + process.getName() + " ... ") : "..."));

				if (os.indexOf("win") >= 0) {

					rt.exec("taskkill /F" + pid);

				} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

					rt.exec("kill -s SIGKILL " + pid);
				}

			} catch (IOException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}

			log.info("End of kill process");
		}

	}

	private void traceProcessesStats() {

		for (Map.Entry<Short, Pair<Long, Long>> entry : runningProcesses.entrySet()) {

			Short id = entry.getKey();
			Pair<Long, Long> pair = entry.getValue();

			Proc process = processes.stream().filter(e -> e.getId() == id).findFirst().orElse(null);

			if (process != null) {

				log.debug("[Pid = " + pair.getLeft() + "] " + process.getName() + " running since "
						+ Instant.ofEpochMilli(pair.getRight()).toString());
			}
		}
	}

	private boolean loadProcesses() {

		boolean res = true;

		if (!CollectionUtils.isEmpty(processes)) {

			log.info("Starting to load [{}] processes...", processes.size());

			for (Proc proc : processes) {

				if (proc.isEnabled() && !loadProcess(proc)) {

					res = false;

					break;
				}
			}

			res &= checkAllProcessesStatus();

			log.info("OPENITS SERVER is ready");
		}

		if (!res) {

			unload();
		}

		return res;
	}

	private boolean loadProcess(Short id) {

		boolean res = false;

		if (!CollectionUtils.isEmpty(processes)) {

			Proc proc = processes.stream().filter(e -> e.getId() == id).findFirst().orElse(null);

			if (proc != null && loadProcess(proc)) {

				res = true;
			}
		}

		return res;
	}

	private boolean loadProcess(Proc proc) {

		boolean res = false;

		try {

			if (proc != null) {

				String path = proc.getPath();
				String exec = proc.getExecutable();
				Long timeOutLimit = proc.getTimeOut();
				boolean timeOutExceeded = false;
				List<Short> dependenciesIds = getProcessDepedencies(proc.getDependencies());

				if (proc.getParameters() != null && CollectionUtils.isNotEmpty(proc.getParameters().getParam())) {
				}

				File javaJar = new File(path + exec);

				if (javaJar.exists()) {

					if (CollectionUtils.isNotEmpty(dependenciesIds)) {

						processInfoRepository = new ProcessInfoRepository();

						boolean stopLocal = false;

						Long startInstant = Instant.now().toEpochMilli();

						log.info("Loading dependencies of " + proc.getName());

						while (!stopLocal && !timeOutExceeded) {

							Long currentInstant = Instant.now().toEpochMilli();

							timeOutExceeded = (currentInstant - startInstant) >= timeOutLimit;

							if (!timeOutExceeded) {

								stopLocal = dependenciesIds.stream().allMatch(id -> {

									boolean allRunning = true;

									Iterator<Map.Entry<Short, Pair<Long, Long>>> itRunningProcesses = runningProcesses
											.entrySet().iterator();

									while (allRunning && itRunningProcesses.hasNext()) {

										Map.Entry<Short, Pair<Long, Long>> entry = itRunningProcesses.next();

										Short processId = entry.getKey();

										if (processId.equals(id)) {

											Pair<Long, Long> pair = entry.getValue();
											Long processPid = pair.getLeft();

											ProcessInfo processInfo = processInfoRepository.getByPid(processPid, false);

											allRunning &= processInfo != null && processInfo.getReady() != null
													&& processInfo.getReady().equals(true)
													&& isStillAlive(String.valueOf(processPid));
										}
									}

									return allRunning;
								});
							}

							Thread.sleep(10L);
						}
					}

					if (!timeOutExceeded) {

						launchProc(proc);

						res = true;
					} else {

						log.error(
								"Time out loading process " + proc.getName() + " cannot load all needed dependencies");
					}
				} else {

					log.error("Process path not found. Process: " + proc.getName() + " Path: " + proc.getPath()
							+ proc.getExecutable());
				}

			}

		} catch (InterruptedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}

		return res;
	}

	private void launchProc(Proc proc) {

		String path = proc.getPath();
		String exec = proc.getExecutable();
		boolean restartOnFail = proc.isRestartOnFail();

		try {

			log.info("Launching " + proc.getName() + " ...");

			List<String> commandList = new ArrayList<>();

			commandList.add("java");

			if (!CollectionUtils.isEmpty(proc.getParameters().getParam())) {

				commandList.addAll(proc.getParameters().getParam());
			}

			commandList.addAll(Arrays.asList("-jar", path + exec));

			if (!StringUtils.isEmpty(proc.getXmlAdapter())) {

				commandList.add("--xml.adapter=" + proc.getXmlAdapter());
			}

			String cmdProcess = String.join(" ", commandList.stream().toArray(String[]::new));

			String[] cmdExec = { "sh", "-c", "nohup " + cmdProcess + " &" };

			Process process = Runtime.getRuntime().exec(cmdExec);

			long pid = getPID(cmdProcess);

			if (pid > -1) {

				log.info("Launched " + proc.getName() + " with PID=" + pid);

				if (restartOnFail) {

					runningProcesses.put(proc.getId(), new MutablePair<>(pid, Instant.now().toEpochMilli()));
				}
			}

		} catch (IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		}
	}

	private void unload() {

		String os = System.getProperty("os.name").toLowerCase();

		log.info("Starting to unload [{}] processes...", runningProcesses.size());

		for (Map.Entry<Short, Pair<Long, Long>> entry : runningProcesses.entrySet()) {

			try {

				String pid = entry.getValue().getLeft().toString();

				Runtime rt = Runtime.getRuntime();

				Proc proc = processes.stream().filter(e -> e.getId() == entry.getKey().shortValue()).findFirst()
						.orElse(null);

				if (proc != null) {

					log.info("[Pid = " + entry.getValue() + "]" + " Unloading " + proc.getName() + " ... ");

					if (os.indexOf("win") >= 0) {

						rt.exec("taskkill " + pid);

					} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

						rt.exec("kill -s SIGTERM " + pid);
					}
				}

			} catch (IOException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		runningProcesses.clear();

		log.info("End of unload processes");
	}

	private Proc getProc(Long pid) {

		Proc proc = null;

		if (!MapUtils.isEmpty(runningProcesses)) {

			for (Map.Entry<Short, Pair<Long, Long>> entry : runningProcesses.entrySet()) {

				Short id = entry.getKey();
				Pair<Long, Long> pair = entry.getValue();

				Long pairPid = pair.getKey();

				if (pairPid.equals(pid)) {

					proc = processes.stream().filter(e -> e.getId() == id).findFirst().orElse(null);

					break;
				}
			}
		}

		return proc;
	}

	private long getPID(String commandLine) {

		String os = System.getProperty("os.name").toLowerCase();
		String command = null;
		long pid = -1;

		try {
			Runtime rt = Runtime.getRuntime();

			if (os.indexOf("win") >= 0) {

				// TODO
			} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

				command = "ps -aux | grep \"" + commandLine + "\" | grep -v grep | awk '{print $2}'";

				Process pr = rt.exec(new String[] { "sh", "-c", command });

				InputStreamReader isReader = new InputStreamReader(pr.getInputStream());
				BufferedReader bReader = new BufferedReader(isReader);
				String strLine = null;

				if ((strLine = bReader.readLine()) != null) {

					pid = Long.valueOf(strLine);
				}
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (IOException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return pid;
	}

	private boolean isStillAlive(String pid) {

		String os = System.getProperty("os.name").toLowerCase();
		String command = null;

		if (os.indexOf("win") >= 0) {

			command = "cmd /c tasklist /FI \"PID eq " + pid + "\"";
			return isPidRunning(pid, command);

		} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

			command = "ps -p " + pid;
			return isPidRunning(pid, command);
		}

		log.debug("Default Check alive for Pid: [{}] is false", pid);

		return false;
	}

	private boolean isPidRunning(String pid, String command) {

		try {

			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(command);

			InputStreamReader isReader = new InputStreamReader(pr.getInputStream());
			BufferedReader bReader = new BufferedReader(isReader);
			String strLine = null;

			pr.waitFor();
			
			while ((strLine = bReader.readLine()) != null) {

				if (strLine.contains(pid + " ")) {

					return true;
				}
			}

			return false;

		} catch (Exception ex) {

			log.warn("Got exception using system command [{}].", command, ex);
			log.debug(ExceptionUtils.getStackTrace(ex));

			return true;
		}
	}

	public void stop() {

		this.stop = true;
	}

	public List<Short> getProcessDepedencies(String dependencies) {

		List<Short> processDependencies = null;

		if (StringUtils.isNotBlank(dependencies)) {

			String[] separatedDependencies = dependencies.split(",");

			try {
				if (!separatedDependencies[0].equals("")) {

					for (int i = 0; i < separatedDependencies.length; i++) {

						if (processDependencies == null) {

							processDependencies = new ArrayList<>();
						}

						processDependencies.add(Short.parseShort(separatedDependencies[i]));
					}
				}

			} catch (NumberFormatException e) {

				log.error("Cannot convert dependencies from string to short, check the adapter xml file");
			}
		}

		return processDependencies;
	}

	private boolean isOutOfThresholds(Thresholds thresholdsAdapter, ProcessInfo processInfo, Proc process) {

		if (thresholdsAdapter != null && !CollectionUtils.isEmpty(thresholdsAdapter.getThreshold())
				&& processInfo != null && process != null) {

			List<String> thresholds = thresholdsAdapter.getThreshold();
			for (String threshold : thresholds) {

				try {

					String[] params = threshold.split(",");

					if (params != null && params.length > 2) {

						String field = params[FIELD];
						String operation = params[OPERATION];
						String value = params[VALUE];

						Field processInfoField = ClassesUtil.getField(ProcessInfo.class, field);

						if (processInfoField != null) {

							processInfoField.setAccessible(true);

							Object objValue = processInfoField.get(processInfo);

							if (objValue != null) {

								boolean outOfThresholds = false;

								if (processInfoField.getType() == Double.class && ((operation.equals(GREATER_THAN)
										&& (Double) objValue > Double.parseDouble(value))
										|| (operation.equals(LESS_THAN)
												&& (Double) objValue < Double.parseDouble(value)))) {

									outOfThresholds = true;

								} else if (processInfoField.getType() == Float.class && ((operation.equals(GREATER_THAN)
										&& (Float) objValue > Float.parseFloat(value))
										|| (operation.equals(LESS_THAN)
												&& (Float) objValue < Float.parseFloat(value)))) {
									outOfThresholds = true;

								} else if (processInfoField.getType() == Long.class && ((operation.equals(GREATER_THAN)
										&& (Long) objValue > Long.parseLong(value))
										|| (operation.equals(LESS_THAN) && (Long) objValue < Long.parseLong(value)))) {

									outOfThresholds = true;

								} else if (processInfoField.getType() == Integer.class
										&& ((operation.equals(GREATER_THAN)
												&& (Integer) objValue > Integer.parseInt(value))
												|| (operation.equals(LESS_THAN)
														&& (Integer) objValue < Integer.parseInt(value)))) {

									outOfThresholds = true;
								}

								if (outOfThresholds) {

									log.error(process.getName() + " with pid: " + processInfo.getPid()
											+ " will be closed due to out of thresholds. " + field
											+ (operation.equals(GREATER_THAN) ? " > " : " < ") + value + ", value: "
											+ objValue);

									Long timeStamp = this.runningProcesses.get(process.getId()) != null
											? this.runningProcesses.get(process.getId()).getRight()
											: null;

									AuditHelper.Audit(LogType.LOG_TYPE_PROCESSES,
											LogSubtype.LOG_SUBTYPE_PROCESS_OUT_OF_THRESHOLD, User.USER_SYSTEM,
											LogLevel.ERROR, process.getName(),
											timeStamp != null ? Instant.ofEpochMilli(timeStamp).toString() : "-",
											field + (operation.equals(GREATER_THAN) ? " > " : " < ") + value
													+ ", value: " + objValue);

									return outOfThresholds;
								}
							}
						}
					}

				} catch (NumberFormatException e) {

					log.error(
							"Cannot convert thresholds to number format, check the thresholds values in adapter xml file");
					log.error(e.getStackTrace());

				} catch (IllegalArgumentException e) {
					log.error(e.getStackTrace());

				} catch (IllegalAccessException e) {
					log.error(e.getStackTrace());

				}
			}
		}

		return false;

	}

	private void unloadByPid(String pid, Proc proc) {

		String os = System.getProperty("os.name").toLowerCase();

		try {

			Runtime rt = Runtime.getRuntime();

			if (proc != null) {

				log.info("[Pid = " + pid + "]" + " Unloading " + proc.getName() + " ... ");

				if (os.indexOf("win") >= 0) {

					rt.exec("taskkill " + pid);

				} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

					rt.exec("kill -s SIGTERM " + pid);
				}
			}

		} catch (IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		log.info("End of unload process");
	}

	private boolean checkAllProcessesStatus() {

		boolean res = true;

		try {

			List<Long> pendingLoadProcesses = new ArrayList<>();

			runningProcesses.entrySet().forEach(entry -> pendingLoadProcesses.add(entry.getValue().getLeft()));

			while (!pendingLoadProcesses.isEmpty()) {

				for (Long pid : pendingLoadProcesses) {

					ProcessInfo processInfo = null;

					processInfo = processInfoRepository.getByPid(pid, false);

					if (processInfo != null && Boolean.TRUE.equals(processInfo.getReady())) {

						// log.info("Process: " + processInfo.getMain() + " is ready");

						Proc proc = getProc(pid);

						if (proc != null) {

							log.info("Process " + proc.getName() + " is ready");
						}

						pendingLoadProcesses.remove(pid);

						break;
					}
				}

				Thread.sleep(100);
			}

//			for (Map.Entry<Short, Pair<Long, Long>> entry : runningProcesses.entrySet()) {
//
//				Long pid = entry.getValue().getLeft();
//				ProcessInfo processInfo = null;
//
//				while (!stop && (processInfo == null || Boolean.FALSE.equals(processInfo.getReady()))) {
//
//					processInfo = processInfoRepository.getByPid(pid, false);
//
//					Thread.sleep(100);
//				}
//
//				if (processInfo != null && Boolean.TRUE.equals(processInfo.getReady())) {
//
//					log.info("Process: " + processInfo.getMain() + "is ready");
//					
//				} else {
//					res = false;
//					break;
//				}
//
//			}

		} catch (InterruptedException e) {

			res = false;
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}

		return res;
	}
}
