from __future__ import print_function, unicode_literals, division
import json
import random
try:
    from http.server import HTTPServer, BaseHTTPRequestHandler
except ImportError:
    # Probably using Python2
    from BaseHTTPServer import HTTPServer, BaseHTTPRequestHandler
    range = xrange


def random_table():
    width = random.randint(2, 10)
    height = random.randint(1, 6)
    table = []
    for _ in range(height):
        table.append([])
        for __ in range(width):
            table[-1].append(random.randint(1, 30000))
    return table


class Handler(BaseHTTPRequestHandler):

    def do_GET(self):
        self.send_response(200)
        if self.path == '/index.html':
            self.send_header('Content-type', 'text/html')
            self.end_headers()
            with open('index.html', 'rb') as f:
                self.wfile.write(f.read())
        else:
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            self.wfile.write('{"server_type": "Python"}'.encode())

    def do_POST(self):
        try:
            content_length = int(self.headers['Content-Length'])
            content = self.rfile.read(content_length).decode()
            data = json.loads(content)
            self.send_response(200)
            self.send_header('Content-type', 'application/json')
        except ValueError:
            self.send_response(400)
            data = {"status_code": 400, "message": "Your JSON was broken"}
            print(content)
        else:
            for key in data:
                try:  # to reverse
                    data[key] = data[key][::-1]
                except:  # anything that we can't reverse we'll leave alone
                    pass
        self.end_headers()
        data['random_table'] = random_table()
        self.wfile.write(json.dumps(data).encode())


server = HTTPServer(('', 8765), Handler)
server.serve_forever()
