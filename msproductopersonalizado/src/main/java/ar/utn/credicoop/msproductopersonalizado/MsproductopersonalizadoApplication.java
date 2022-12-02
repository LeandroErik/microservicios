package ar.utn.credicoop.msproductopersonalizado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsproductopersonalizadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsproductopersonalizadoApplication.class, args);
	}

}
