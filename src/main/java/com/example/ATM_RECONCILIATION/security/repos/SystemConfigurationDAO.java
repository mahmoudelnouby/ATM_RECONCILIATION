package com.example.ATM_RECONCILIATION.security.repos;

import com.example.ATM_RECONCILIATION.security.models.WLSConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SystemConfigurationDAO extends JpaRepository<WLSConfiguration, String>{

	@Query(value="select * from bpm_adminstration.web_logic_conf" , nativeQuery = true)
	public WLSConfiguration getWLSConfig();

}
