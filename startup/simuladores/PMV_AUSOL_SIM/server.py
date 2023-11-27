#!/usr/bin/env python3
"""
HTTP server in python for simulate PMV AUSOL
Usage::
    ./server.py [<port>] [<json_respond_file>]
"""
from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import urlparse 
import logging
import json

class S(BaseHTTPRequestHandler):
    def _set_response(self):
        self.send_response(200)
        if self.path == '/status':        
            self.send_header('Content-type', 'application/json')
        else:
            self.send_header('Content-type', 'text/html')
        self.end_headers()

    def do_GET(self):
        if self.path == '/status':            
            with open(json_file_name) as json_file:
                json_response = json.load(json_file)
            response = json.dumps(json_response)
            response = bytes(response, 'utf-8')
            self._set_response()
            self.wfile.write(response)
        if '/send_text' in self.path:  
            query = urlparse(self.path).query
            query_components = dict(qc.split("=") for qc in query.split("&"))
            if "l11" in query_components:
                l11 = query_components["l11"]
            if "l12" in query_components:
                l12 = query_components["l12"]
            if "l13" in query_components:
                l13 = query_components["l13"]
            if "p1" in query_components:
                p1 = query_components["p1"]
              
            with open(json_file_name,'r+') as json_file:
                json_response = json.load(json_file)
                content_string_vector = ["","","",""]
                if "l11" in locals():
                  content_string_vector[0] = l11;  
                  
                if "l12" in locals():
                  content_string_vector[1] = l12;
                         
                if "l13" in locals():
                  content_string_vector[2] = l13;
                         
                if "p1" in locals():
                    content_string_vector[3] = p1;
                    
                json_response["content"] = content_string_vector[0] + '#' + content_string_vector[1] + '#' + content_string_vector[2] + '#' + content_string_vector[3]
                json_file.seek(0)
                json_file.truncate()
                json.dump(json_response,json_file)
                self._set_response()
            self.wfile.write("OK".format(self.path).encode('utf-8'))
        if '/send_alternant' in self.path:  
            query = urlparse(self.path).query
            query_components = dict(qc.split("=") for qc in query.split("&"))
            if "l11" in query_components:
                l11 = query_components["l11"]
            if "l12" in query_components:
                l12 = query_components["l12"]
            if "l13" in query_components:
                l13 = query_components["l13"]
            if "p1" in query_components:
                p1 = query_components["p1"]
            if "l21" in query_components:
                l21 = query_components["l21"]
            if "l22" in query_components:
                l22 = query_components["l22"]
            if "l23" in query_components:
                l23 = query_components["l23"]
            if "p2" in query_components:
                p2 = query_components["p2"]
              
            with open(json_file_name,'r+') as json_file:
                json_response = json.load(json_file)
                content_string_vector = ["","","","","","","",""]
                if "l11" in locals():
                    content_string_vector[0] = l11;  
                  
                if "l12" in locals():
                    content_string_vector[1] = l12;
                         
                if "l13" in locals():
                    content_string_vector[2] = l13;
                         
                if "p1" in locals():
                    content_string_vector[3] = p1;
                    
                if "l21" in locals():
                    content_string_vector[4] = l21;  
                  
                if "l22" in locals():
                    content_string_vector[5] = l22;
                         
                if "l23" in locals():
                    content_string_vector[6] = l23;
                         
                if "p2" in locals():
                    content_string_vector[7] = p2;
                    
                json_response["content"] = content_string_vector[0] + '#' + content_string_vector[1] + '#' + content_string_vector[2] + '#' + content_string_vector[3] + '#' + content_string_vector[4] + '#' + content_string_vector[5] + '#' + content_string_vector[6] + '#' + content_string_vector[7];
                json_file.seek(0)
                json_file.truncate()
                json.dump(json_response,json_file)
                self._set_response()
            self.wfile.write("OK".format(self.path).encode('utf-8'))
        if '/send_shutdown' in self.path:    
            with open(json_file_name,'r+') as json_file:  
                json_response = json.load(json_file)
                json_response["content"] = ""
                json_file.seek(0)
                json_file.truncate()
                json.dump(json_response,json_file)
                self._set_response()
            self.wfile.write("OK".format(self.path).encode('utf-8'))
            
        logging.info("GET request,\nPath: %s\nHeaders:\n%s\n", str(self.path), str(self.headers))

    def do_POST(self):
        content_length = int(self.headers['Content-Length']) # <--- Gets the size of data
        post_data = self.rfile.read(content_length) # <--- Gets the data itself
        logging.info("POST request,\nPath: %s\nHeaders:\n%s\n\nBody:\n%s\n",
                str(self.path), str(self.headers), post_data.decode('utf-8'))
        if '/send_text' in self.path: 
            query = post_data.decode('utf-8')
            query_components = dict(qc.split("=") for qc in query.split("&"))
            if "l11" in query_components:
                l11 = query_components["l11"]
            if "l12" in query_components:
                l12 = query_components["l12"]
            if "l13" in query_components:
                l13 = query_components["l13"]
            if "p1" in query_components:
                p1 = query_components["p1"]
                          
            with open(json_file_name,'r+') as json_file:
                json_response = json.load(json_file)
                content_string_vector = ["","","",""]
                content_string = json_response["content"].split('#')
                if "l11" in locals():
                  content_string_vector[0] = l11;  
                  
                if "l12" in locals():
                  content_string_vector[1] = l12;
                               
                if "l13" in locals():
                  content_string_vector[2] = l13;
                         
                if "p1" in locals():
                    content_string_vector[3] = p1;
                                     
                json_response["content"] = content_string_vector[0] + '#' + content_string_vector[1] + '#' + content_string_vector[2] + '#' + content_string_vector[3]
                json_file.seek(0)
                json_file.truncate()
                json.dump(json_response,json_file)
                self._set_response()
            self.wfile.write("OK".format(self.path).encode('utf-8'))
        
        self.wfile.write("POST request for {}".format(self.path).encode('utf-8'))

def run(server_class=HTTPServer, handler_class=S, port=8080):
    logging.basicConfig(level=logging.INFO)
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    logging.info('Starting httpd...\n')
 
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass
    httpd.server_close()
    logging.info('Stopping httpd...\n')

if __name__ == '__main__':
    from sys import argv
    if len(argv) == 3:
        json_file_name= argv[2]
        run(port=int(argv[1]))
    if len(argv) == 2:
        json_file_name= 'status_response.json'
        run(port=int(argv[1]))
    else:
        json_file_name= 'status_response.json'
        run()