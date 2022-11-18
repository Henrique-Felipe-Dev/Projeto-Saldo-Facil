"""
import requests
import grequests
from bs4 import BeautifulSoup

def helloworld():

    response = requests.get('https://g1.globo.com/')

    content = response.content

    site = BeautifulSoup(content, 'html.parser')

    noticia = site.find('div', attrs={'class': 'feed-post-body'})

    titulo = noticia.find('a', attrs={'class': 'feed-post-link'})

    return titulo.text

"""

import grequests
import requests
import aiohttp
import asyncio
from bs4 import BeautifulSoup

async def main():
    """
    response = requests.get('https://g1.globo.com/')

    content = response.content

    site = BeautifulSoup(content, 'html.parser')

    noticia = site.find('div', attrs={'class': 'feed-post-body'})

    titulo = noticia.find('a', attrs={'class': 'feed-post-link'})

    return titulo.text
    """



    return 'resp'


def helloworld():
    resp = asyncio.run(main())

    return resp
