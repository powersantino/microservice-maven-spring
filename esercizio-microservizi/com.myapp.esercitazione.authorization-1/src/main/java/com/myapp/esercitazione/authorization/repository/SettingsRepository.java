package com.myapp.esercitazione.authorization.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.esercitazione.authorization.model.SettingModel;;

public interface SettingsRepository extends JpaRepository<SettingModel, String>{
	Optional<SettingModel> findByKey(String key);
}
