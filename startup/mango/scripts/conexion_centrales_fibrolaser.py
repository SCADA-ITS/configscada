import requests

CONNECTED = "1"
NOT_CONNECTED = "0"

centrales_fibrolaser = {
    "CEN-LIST-DP1":"http://172.21.33.96",
    "CEN-LIST-DP2":"http://172.21.49.96",
    "CEN-LIST-CA":"http://172.21.57.56",
    "CEN-LIST-CZ":"http://172.21.33.106"
    }

path = "/home/revenga/"

def central_fibrolaser_connection(central_fibrolaser, url, file):
    response = requests.get(url.values)
    status_code = response.status_code
    if (status_code == 200):
        file.write(f"{central_fibrolaser}:{CONNECTED}")
    else:
        file.write(f"{central_fibrolaser}:{NOT_CONNECTED}")


try:
    with open (f"{path}FIBROLASER_STATES.txt", "w") as file:
        for central_fibrolaser, url in centrales_fibrolaser:
            central_fibrolaser_connection(central_fibrolaser, url, file)
            file.write("\n")
            
except requests.RequestException as e:
    print(f"Error en la petición: {e}")

