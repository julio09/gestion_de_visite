package com.example.ProjetM1;

import com.example.ProjetM1.Entity.Medicin;
import com.example.ProjetM1.Repository.MedicinRepositoryInterface;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class ProjetM1ApplicationTests {

	@Autowired
	private MedicinRepositoryInterface medicinRepositoryInterface;

	@Test
	void contextLoads() {
		Medicin medicin = new Medicin();
		medicin.setId(null);
		medicin.setNom("RAKOTO");
		medicin.setPrenom("bobo");
		medicin.setGrade("Dentiste");
		Medicin medicin1 = medicinRepositoryInterface.save(medicin);
		Assertions.assertThat(medicin1).isNotNull();
		Assertions.assertThat(medicin1.getId()).isGreaterThan(0);
	}

}
