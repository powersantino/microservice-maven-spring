package com.myapp.esercitazione.postservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.myapp.esercitazione.postservice.entity.PostEntity;
import com.myapp.esercitazione.postservice.repository.PostRepository;


/*
 * Classe di debug per il popolamento del DB 
 */
@Component
public class DataLoader implements ApplicationRunner {
	private PostRepository postRepository;
	@Autowired
    public DataLoader(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Test insert
		String title = "Title ";
		String body = "Body ";
		PostEntity testPost ;		
		for (int i = 0; i< 10; i ++) {
			testPost = new PostEntity();
			testPost.setIdUser(1);
			testPost.setTitle(title+ i);			 
			StringBuilder myBody = new StringBuilder();
			for(int a = 0; a < 30; a ++) {				
				myBody.append(body + i + " ");
			}
			testPost.setBody(myBody.toString());
			postRepository.save(testPost);
		}
	}
}

	
	
