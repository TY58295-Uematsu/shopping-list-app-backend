package com.shoppinglistApp

import com.shoppinglistApp.domain.ItemRequest
import com.shoppinglistApp.domain.UserRepository
import com.shoppinglistApp.domain.UserRequest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests(
	@Autowired val restTemplate: TestRestTemplate,
	@LocalServerPort val port: Int
)  {
	@Autowired
	private lateinit var userRepository: UserRepository

	@Test
	fun contextLoads() {
	}
	@Test
	fun `最初のテスト`() {
		assertThat(1+2, equalTo(3))
	}
	@Test
	fun `GETリクエストはOKステータスを返す`() {
		val userCount = userRepository.count()
		val userIdToTest = 1L
		val response = restTemplate.getForEntity("http://localhost:$port/users/$userIdToTest", String::class.java)
		// レスポンスのステータスコードは OK である。
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `itemGETリクエストはOKステータスを返す`() {
		val response = restTemplate.getForEntity("http://localhost:$port/lists/all/1", String::class.java)
		// レスポンスのステータスコードは OK である。
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `POSTリクエスト1はOKステータスを返す`() {
		// localhost/todos に POSTリクエストを送る。このときのボディは {"text": "hello"}
		val request1 = UserRequest(userName = "hello100", mail = "example1@mail.com", rootShop = 1)
		val response1 = restTemplate.postForEntity("http://localhost:$port/users", request1, String::class.java)
		// レスポンスのステータスコードは OK であること。
		assertThat(response1.statusCode, equalTo(HttpStatus.OK))
	}
	@Test
	fun `POSTリクエスト2はOKステータスを返す`() {
		// localhost/todos に POSTリクエストを送る。このときのボディは {"text": "hello"}
		val request2 = UserRequest(userName = "hello101", mail = "example2@mail.com", rootShop = 2)
		val response2 = restTemplate.postForEntity("http://localhost:$port/users", request2, String::class.java)
		// レスポンスのステータスコードは OK であること。
		assertThat(response2.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `itemsPOSTリクエストはOKステータスを返す`() {
		// localhost/todos に POSTリクエストを送る。このときのボディは {"text": "hello"}
		val request = ItemRequest(item = "hello101", buy = false, shopId = 1, userId = 1)
		val response = restTemplate.postForEntity("http://localhost:$port/lists/items", request, String::class.java)
		// レスポンスのステータスコードは OK であること。
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}
}
