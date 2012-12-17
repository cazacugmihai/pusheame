package net.kaleidos.pusheame

import groovy.json.JsonSlurper

class InstagramService {

	static transactional = false

	Random random = new Random()

	def pictures = [
		[url:"http://distilleryimage1.s3.amazonaws.com/d18c4ca4483911e29d8c22000a1fbd8b_7.jpg", latitude:63.974079132, longitude:-22.576965332],
		[url:"http://distilleryimage8.s3.amazonaws.com/12d89e2e483511e2ace922000a1f90f6_7.jpg", latitude:48.866475499, longitude:2.350620338],
		[url:"http://distilleryimage7.s3.amazonaws.com/f9f0623c483b11e2900e22000a1f96c7_7.jpg", latitude:14.642080307, longitude:121.037803649],
		[url:"http://distilleryimage7.s3.amazonaws.com/db24bde0483a11e29e0522000a1fa50c_7.jpg", latitude:13.745900735, longitude:100.535358315],
		[url:"http://distilleryimage8.s3.amazonaws.com/8c3f48cc484311e29c6822000a1f9688_7.jpg", latitude:13.76815319, longitude:100.567634582],
		[url:"http://distilleryimage9.s3.amazonaws.com/0c73548a483e11e2b83422000a1f9e4c_7.jpg", latitude:13.667921984, longitude:100.457830693],
		[url:"http://distilleryimage9.s3.amazonaws.com/9c8b4e50483011e286b422000a9d0dd8_7.jpg", latitude:-12.107051993, longitude:-76.974320679],
		[url:"http://distilleryimage0.s3.amazonaws.com/36bdd8e4484011e2ac5022000a9f18b3_7.jpg", latitude:22.283016667, longitude:114.173061111],
		[url:"http://distilleryimage3.s3.amazonaws.com/43e80586485311e2974222000a1fbdac_5.jpg", latitude:40.730948977, longitude:-73.974979785],
		[url:"http://distilleryimage10.s3.amazonaws.com/055a474e484811e2b5c422000a1f9a53_5.jpg", latitude:-23.585132598, longitude:-46.689842224],
		[url:"http://distilleryimage10.s3.amazonaws.com/31da4f14484b11e2bb3522000a1fb076_5.jpg", latitude:51.501407104, longitude:-2.546291136],
		[url:"http://distilleryimage6.s3.amazonaws.com/a2d20328484711e2bd6322000a1fa42a_5.jpg", latitude:-6.371333333, longitude:106.890333333],
		[url:"http://distilleryimage2.s3.amazonaws.com/4d1433a6484d11e2a61a22000a9e06f0_5.jpg", latitude:13.724960813, longitude:100.535094738],
		[url:"http://distilleryimage6.s3.amazonaws.com/a7746606485011e2aeda22000a1f973b_5.jpg", latitude:51.772697448, longitude:-1.429522156],
		[url:"http://distilleryimage4.s3.amazonaws.com/efc99f18485311e2a86422000a1f9839_5.jpg", latitude:38.903333333, longitude:-77.061833333],
		[url:"http://distilleryimage2.s3.amazonaws.com/0fe1e300484a11e2ac5022000a9f18b3_5.jpg", latitude:48.885036412, longitude:2.324883318],
		[url:"http://distilleryimage2.s3.amazonaws.com/23439116484d11e28df322000a1f9367_5.jpg", latitude:48.862197875, longitude:2.299952507],
		[url:"http://distilleryimage2.s3.amazonaws.com/82767a92485511e2b0c122000a9f17df_5.jpg", latitude:14.649191856, longitude:121.04837799],
		[url:"http://distilleryimage8.s3.amazonaws.com/5f2acc48485311e2901122000a9f1939_5.jpg", latitude:-21.211000442, longitude:-50.438667297],
		[url:"http://distilleryimage4.s3.amazonaws.com/efc99f18485311e2a86422000a1f9839_5.jpg", latitude:38.903333333, longitude:-77.061833333],
		[url:"http://distilleryimage4.s3.amazonaws.com/a0e286fc484f11e291f422000a1fbd6e_5.jpg", latitude:13.639354705, longitude:100.645126342],
		[url:"http://distilleryimage6.s3.amazonaws.com/7c1b9884484c11e28b2322000a1fbe1b_5.jpg", latitude:13.856845216, longitude:100.661960756],
		[url:"http://distilleryimage2.s3.amazonaws.com/23439116484d11e28df322000a1f9367_5.jpg", latitude:48.862197875, longitude:2.299952507],
		[url:"http://distilleryimage9.s3.amazonaws.com/895e0b2e484e11e28a2c22000a9f15d9_5.jpg", latitude:-22.983909064, longitude:-43.209854781],
		[url:"http://distilleryimage2.s3.amazonaws.com/d477c076485011e29d7122000a1f97c6_5.jpg", latitude:14.532927168, longitude:120.981896152],
		[url:"http://distilleryimage5.s3.amazonaws.com/f51b08b4484c11e2b59422000a9f13f8_5.jpg", latitude:38.58017722, longitude:-121.49377],
		[url:"http://distilleryimage5.s3.amazonaws.com/2f51cd8e485611e2a2e022000a1faf45_5.jpg", latitude:51.515046578, longitude:-0.097342026]
	]

	public randomPicture() {
		def size = pictures.size()

		return pictures[random.nextInt(size-1)]
	}

	public List getPictures() {
		def clientId = "15caef3eae0245c382291b28d0485831"

		def url = "https://api.instagram.com/v1/media/popular?client_id=${clientId}"
		def content = new URL(url).getText("UTF-8")
		def slurper = new JsonSlurper()
		def response = slurper.parseText(content)

		def result = []

		response.data.each { picture ->
			def map = [:]

			if (picture.location) {
				map.instagramId = picture.id
				map.title = picture.caption?.text
				//map.url = picture.images.standard_resolution.url
				map.url = picture.images.thumbnail.url
				map.author = picture.caption?.from?.username
                map.latitude = picture.location.latitude
                map.longitude = picture.location.longitude

				result << map
			}
		}

		return result
	}

}