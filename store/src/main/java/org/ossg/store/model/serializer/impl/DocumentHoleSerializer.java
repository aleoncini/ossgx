package org.ossg.store.model.serializer.impl;

import javax.enterprise.context.ApplicationScoped;

import org.bson.Document;
import org.ossg.store.model.Hole;
import org.ossg.store.model.serializer.HoleSerializer;

@ApplicationScoped
public class DocumentHoleSerializer implements HoleSerializer<Document>{

	@Override
	public Document serialize(Hole hole) {
		if (hole == null
				|| (hole.getHcp() == 0) 
	            || (hole.getPar() == 0) 
	        ) {
	            throw new IllegalArgumentException("Hole object contains invalid data");
	        }

	        return new Document("hcp", hole.getHcp())
	                .append("par", hole.getPar());
	}

	@Override
	public Hole deserialize(Document document) {
        int hcp = document.getInteger("hcp");
        int par = document.getInteger("par");

        if (document == null
				|| (hcp == 0)
				|| (par == 0)
			) {
			throw new IllegalArgumentException("Unable to deserialize Hole object. Document contains invalid data");
        }


        return new Hole().setHcp(hcp).setPar(par);
	}
    
}