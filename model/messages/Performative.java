package SMATP3.model.messages;

public enum Performative {
	REQUEST, // requete pour se déplacer soit meme
	INFORM, // information comme une réponse négative ou positive
	INQUIRE, // s'enquiert de l'état ou ???
	ORDER; // donne l'ordre de se déplacer 
}
