import csv

def generate_docker_compose(csv_file, output_file):
    with open(csv_file, 'r') as file:
        reader = csv.DictReader(file)
        haproxy = list(reader)

    with open('haproxy.cfg', 'w') as file:
        server_names = []

        for plc in haproxy:
            server_name = plc["Server_Name"]
            if server_name not in server_names:
                server_names.append(server_name)

        file.write('defaults\n')
        file.write('  timeout connect 5000ms\n')
        file.write('  timeout client 50000ms\n')
        file.write('  timeout server 50000ms\n')
        file.write('\n')

        for server_name in server_names:
            pair = [plc for plc in haproxy if plc["Server_Name"] == server_name]

            file.write(f'frontend frontend_{server_name}\n')
            file.write(f'  bind *:{pair[0]["HAProxy_Port"]}\n')
            file.write('  mode tcp\n')
            file.write(f'  timeout client 30s\n')
            file.write(f'  maxconn {pair[0]["Max_Connections"]}\n')
            file.write(f'  default_backend backend_{server_name}\n')
            file.write('\n')

            file.write(f'backend backend_{server_name}\n')
            file.write('  mode tcp\n')
            file.write(f'  timeout server 30s\n')
            file.write(f'  server server1 {pair[0]["PLC_IP"]}:{pair[0]["PLC_Port"]} check\n')
            file.write(f'  server server2 {pair[1]["PLC_IP"]}:{pair[1]["PLC_Port"]} check backup\n')
            file.write('\n')

        # Agregar la configuración de estadísticas general
        file.write('listen stats\n')
        file.write('  bind *:10000\n')
        file.write('  mode http\n')
        file.write('  stats enable\n')
        file.write('  stats hide-version\n')
        file.write('  stats realm Haproxy\\ Statistics\n')
        file.write('  stats uri /haproxy_stats\n')
        file.write('  stats auth admin:admin\n')
        file.write('\n')

    with open(output_file, 'w') as file:
        file.write('version: "3"\n\n')
        file.write('services:\n')
        file.write('  haproxy:\n')
        file.write('    image: haproxy:latest\n')
        file.write('    ports:\n')
        unique_ports = list(set(plc["Docker_Port"] for plc in haproxy))
        for port in unique_ports:
            file.write(f'      - {port}:{port}\n')
        # Agregar el puerto de las estadísticas
        file.write('      - 10000:10000\n')
        file.write('    volumes:\n')
        file.write('      - ./haproxy:/usr/local/etc/haproxy/haproxy.cfg\n')
        file.write('    restart: unless-stopped\n')
        file.write('    networks:\n')
        file.write('      - backend\n')
        file.write('\n')
        file.write('networks:\n')
        file.write('  backend:\n')
        file.write('    driver: bridge\n')
        file.write('\n')

generate_docker_compose('haproxy.csv', 'docker-compose.yml')
