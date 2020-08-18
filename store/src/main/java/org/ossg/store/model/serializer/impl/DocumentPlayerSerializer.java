package org.ossg.store.model.serializer.impl;

import javax.enterprise.context.ApplicationScoped;

import org.bson.Document;
import org.ossg.store.model.Player;
import org.ossg.store.model.serializer.PlayerSerializer;

@ApplicationScoped
public class DocumentPlayerSerializer implements PlayerSerializer<Document> {
    @Override
	public Document serialize(Player player) {

		if (player == null
				|| (player.getId() == null) 
	            || (player.getId().length() == 0) 
	            || (player.getName() == null) 
	            || (player.getName().length() == 0) 
	        ) {
	            throw new IllegalArgumentException("Player object contains invalid data");
	        }

		Document document = new Document("id", player.getId()).append("name", player.getName());
		if((player.getEmail() != null) && (player.getEmail().length() > 0)){
			document.append("email", player.getEmail());
		}	
	    return document;
	}

	@Override
	public Player deserialize(Document document) {
		
        String id = document.getString("id");
        String name = document.getString("name");
        String email = document.getString("email");

		if (document == null
				|| (id == null)
				|| (id.length() == 0)
				|| (name == null)
				|| (name.length() == 0)
			) {
			throw new IllegalArgumentException("Unable to desiarilize Player object. Document contains invalid data");
        }


		Player player = new Player().setId(id).setName(name);
		if(
			(email != null)
			&& (email.length() > 0)
		) {
			player.setEmail(email);
		}
        return player;
	}

}