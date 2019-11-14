package com.myapp.esercitazione.authorization.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myapp.esercitazione.authorization.model.SettingModel;
import com.myapp.esercitazione.authorization.repository.SettingsRepository;

@Component
public class SettingsService {
	@Autowired
	private SettingsRepository settingsRepository;
	public Optional<SettingModel> getByKey(String key){
		return settingsRepository.findByKey(key);
	} 
	
	public SettingModel save(SettingModel setting){
		return settingsRepository.save(setting);
	}
}
