/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync                                         
@RestController                                      
@EnableBinding(Source.class)                         
public class SampleMessagingApplication {            
                                                     
	@Autowired
	private Source source;
	
	private SampleBackground background;

	@RequestMapping("/")
	public String home() {
		String msg = "Hello";
		boolean isSuccess = source.output().send(
				MessageBuilder.withPayload(msg).build());
		System.out.println(isSuccess);
		return msg;
	}

	@RequestMapping("/foo")
	public String foo() throws InterruptedException {
//		background.background();
		return "foo";
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleMessagingApplication.class, args);
	}

}