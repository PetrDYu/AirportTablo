import requests
import json

if __name__ == '__main__':
    res = requests.get('https://api.rasp.yandex.net/v3.0/schedule/', params={
        'apikey': 'a1f60bd5-7507-407d-a4eb-17a1c9683925',
        'station': 'SVO',
        'date': '2022-06-06',
        'transport_types': 'plane',
        'system': 'iata',
        'event': 'arrival',
        'limit': '200',
        'offset': '100'
    },
                       headers={
                           'Authorization': 'a1f60bd5-7507-407d-a4eb-17a1c9683925'
                       })
    print(res.request.body)

    with open('SVO_ar.json', 'w', encoding='utf-8') as f:
        f.write(json.dumps(json.loads(res.text.encode('utf-8')), indent=4, sort_keys=True, ensure_ascii=False))
    print(json.dumps(json.loads(res.text.encode('utf-8')), indent=4, sort_keys=True, ensure_ascii=False))
