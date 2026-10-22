package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.TubeEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.Tube;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.ElectricalPanelEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.entity.manager.FanEntityManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TargetFansProcessorThread implements Runnable {

	private FanEntityManager fanEntityManager;
	private FansProcessor fansProcessor;
	private TubeEntityManager tubeEntityManager;
	private ElectricalPanelEntityManager electricalPanelEntityManager;

	private boolean stop;
	private boolean alive;

	public TargetFansProcessorThread(final FansProcessor fansProcessor, final FanEntityManager fanEntityManager,
			final TubeEntityManager tubeEntityManager,
			final ElectricalPanelEntityManager electricalPanelEntityManager) {

		super();
		this.fanEntityManager = fanEntityManager;
		this.fansProcessor = fansProcessor;
		this.tubeEntityManager = tubeEntityManager;
		this.electricalPanelEntityManager = electricalPanelEntityManager;
	}

	// Only stop fan with target fan in tube (auto mode without fire)
	private void checkFansAlarmed() {

		List<Pair<Long, Long>> alarmedFans = this.fanEntityManager.getAlarmedFans();

		if (CollectionUtils.isNotEmpty(alarmedFans)) {

			alarmedFans = alarmedFans.stream().filter(alarmedFan -> {

				Tube tube = null;
				
				if (this.fanEntityManager.isFanState(alarmedFan.getRight(), alarmedFan.getLeft(),
						FanEntityManager.FAN_STATE_VALUE_START)
						|| this.fanEntityManager.isFanState(alarmedFan.getRight(), alarmedFan.getLeft(),
								FanEntityManager.FAN_STATE_VALUE_REVERSE)) {
					
					Element elemenTube = tubeEntityManager.getFirtTubeByChild(
							ElementType.ELEMENT_TYPE_FAN, alarmedFan.getRight());

					 tube = tubeEntityManager.getTube(elemenTube);
				}
				
				return tube != null && tube.getTargetFans() != null;

			}).collect(Collectors.toList());

			if (CollectionUtils.isNotEmpty(alarmedFans)) {

				log.info("Send stop fans " + alarmedFans + ", they have alarms");
				this.fansProcessor.addCommands(alarmedFans, null, FansProcessor.SEND_FAN_STOP);
			}
		}
	}

	private void startFans(Long nFansToStart, Long tubeId) {

		List<Pair<Long, Long>> fanTostart = new ArrayList<>();
		Map<Long, Integer> nFansStillBeStarted = new HashedMap<>();

		List<Pair<Long, Long>> candidatesTostart = fanEntityManager.getFansToStart(tubeId);

		// Filter by electrical panel
		if (CollectionUtils.isNotEmpty(candidatesTostart)) {

			log.info("Fans candidates: " + candidatesTostart + " in tube: " + tubeId);

			int count = 0;
			for (int index = 0; index < candidatesTostart.size() && count < nFansToStart.intValue(); index++) {

				Long electricalPanelId = candidatesTostart.get(index).getKey();
				Integer nFans = nFansStillBeStarted.get(electricalPanelId);

				if (nFans == null) {

					Integer maxNfanStarted = electricalPanelEntityManager.getMaxNfansStart(electricalPanelId);
					Long fanStarted = fanEntityManager.getStartedFanByElectricalPanelMax(electricalPanelId);

					if (ObjectUtils.allNotNull(maxNfanStarted, fanStarted)) {

						nFans = maxNfanStarted - fanStarted.intValue();
					}
				}

				if (nFans != null && nFans > 0) {

					log.info("You can still start " + nFans + " fans in the electrical panel "
							+ electricalPanelId);
					
					count++;
					fanTostart.add(candidatesTostart.get(index));
					nFans--;

					nFansStillBeStarted.put(electricalPanelId, nFans);

				} else {

					log.info("It is not allowed to start more fans in electrical panel " + electricalPanelId);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(fanTostart)) {

			boolean windDirection = true;

			Element elementTube = new Element(ElementType.ELEMENT_TYPE_TUBE, tubeId);

			if (tubeEntityManager.getCurrentWindDirectionByTube(elementTube) == null) {

				windDirection = tubeEntityManager.checkWindDirection(elementTube);

			} else {

				windDirection = tubeEntityManager.getCurrentWindDirectionByTube(elementTube).booleanValue();
			}

			if (windDirection) {

				log.info("Send start fans " + fanTostart);
				this.fansProcessor.addCommands(fanTostart, tubeId, FansProcessor.SEND_FAN_START);


			} else {

				log.info("Send start in reverse fans " + fanTostart);
				this.fansProcessor.addCommands(fanTostart, tubeId, FansProcessor.SEND_FAN_START_REVERSE);
			}

		}
	}

	private void stopFans(Long nFansToStop, Long tubeId) {

		List<Pair<Long, Long>> fanToStop = fanEntityManager.getFansToStop(nFansToStop, tubeId);

		if (CollectionUtils.isNotEmpty(fanToStop)) {

			log.info("Send stop fans " + fanToStop);
			this.fansProcessor.addCommands(fanToStop, tubeId, FansProcessor.SEND_FAN_STOP);
		}
	}

	@Override
	public void run() {

		try {
			log.info(Thread.currentThread().getName() + " is running");

			while (!stop) {

				checkFansAlarmed();

				Set<Long> tubesWithPendingwork = fansProcessor.getTubeWithPendingWork();

				Map<Long, Tube> tubes = this.tubeEntityManager.getTubes();

				if (tubes != null && tubesWithPendingwork != null) {

					for (Entry<Long, Tube> tubeEntry : tubes.entrySet()) {

						Tube tube = tubeEntry.getValue();
						Long tubeId = tubeEntry.getKey();

						if (tube != null && tubeId != null && tube.getTargetFans() != null
								&& !tubesWithPendingwork.contains(tubeId)) {

							Long targetFans = tube.getTargetFans();
							Long startedFans = this.fanEntityManager.getStartedFanByTube(tubeId);

							if (targetFans > startedFans) {

								log.info("Target fans (" + targetFans + ") < " + "started fans (" + startedFans
										+ ") in tube " + tubeId);

								Long nFansToStart = targetFans - startedFans;

								startFans(nFansToStart, tubeId);

							} else if (targetFans < startedFans) {

								log.info("Target fans (" + targetFans + ") < " + "startet fans (" + startedFans
										+ ") in tube " + tubeId);

								Long nFansToStop = startedFans - targetFans;

								stopFans(nFansToStop, tubeId);
							}

						}
					}
				}

				Thread.sleep(1000);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();

		} finally {

			this.setAlive(false);

			log.info(Thread.currentThread().getName() + " is stopped");
		}
	}

	public void stop() {

		this.stop = true;
	}

	public boolean isAlive() {

		return alive;
	}

	public void setAlive(boolean alive) {

		this.alive = alive;
	}

	public boolean getStop() {

		return this.stop;
	}
}
