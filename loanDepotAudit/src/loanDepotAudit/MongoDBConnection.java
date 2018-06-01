package loanDepotAudit;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.*;
import static com.mongodb.client.model.Filters.eq;


public class MongoDBConnection {
			
	
/*	public static void main(String[] args) {
				
			String Collection = "EOIDatabase";
			String username = "nitro5";
			String password = "SASuke1!";
			
			
			
			MongoDatabase database = connectToMongoDB(username, password);

				
				MongoCollection<Document> EOICollection = database.getCollection(Collection);
				MongoIterable<String> NamesOfCollections =  database.listCollectionNames();
				
				for(String collection: NamesOfCollections) {
					System.out.println(collection.toString()+"\n");
				}
				Document HomeDoc = EOICollection.find(eq("Id","Home Infromation")).first();
				System.out.println(HomeDoc.toJson());
				int CB = HomeDoc.getInteger("Current Benefits");
				System.out.println(CB);
				
				List<Document> Docs = EOICollection.find().into(new ArrayList<Document>());
				
				
				for (Document Doc: Docs) {
					System.out.println(Doc.toJson());
					//System.out.println(Doc.get("Current Benefits"));

					
				}
				

				BasicDBObject updateQuery = new BasicDBObject();
				updateQuery.append("$set", 
					new BasicDBObject().append("Borrowers", 130072));

				BasicDBObject searchQuery = new BasicDBObject();
				searchQuery.append("Id","Home Infromation");

				//EOICollection.updateMany(searchQuery, updateQuery);		
				

		
	}
*/
	//returns a client 
	public static MongoDatabase connectToMongoDB(String username,String password) {
		String Collection = "EOIDatabase";
		
		String URI = "mongodb://"+username+":"+password+"@wichodb-shard-00-00-czymy.mongodb.net:27017,wichodb-shard-00-01-czymy.mongodb.net:27017,wichodb-shard-00-02-czymy.mongodb.net:27017/test?ssl=true&replicaSet=WichoDB-shard-0&authSource=admin";
		MongoClientURI clienturi = new MongoClientURI(URI);
		MongoClient mongoClient = new MongoClient(clienturi);
		MongoDatabase database = mongoClient.getDatabase("test");
		
		return database;
		
	}
	
	
	public int getCurrentBenefit(MongoCollection<Document> EOICollection) {
		Document HomeDoc = EOICollection.find(eq("Id","Home Infromation")).first();
		int CB = HomeDoc.getInteger("Current Benefits");
		return CB;
	}
	
	public int getCurrentBorrowers(MongoCollection<Document> EOICollection) {
		Document HomeDoc = EOICollection.find(eq("Id","Home Infromation")).first();
		int CB = HomeDoc.getInteger("Borrowers");
		return CB;
	}
	
	public int getValue(MongoCollection<Document> EOICollection, String Id, String key) {
		Document HomeDoc = EOICollection.find(eq("Id",Id)).first();
		int CB = HomeDoc.getInteger(key);
		return CB;
	}
	

	public void updateDocument(MongoCollection<Document> EOICollection , String Id , String key, int value) {
		BasicDBObject updateQuery = new BasicDBObject();
		updateQuery.append("$set", 
			new BasicDBObject().append(key, value));

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("Id",Id);

		EOICollection.updateMany(searchQuery, updateQuery);	
	}

		

	
	
}


