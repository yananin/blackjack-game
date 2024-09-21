import java.util.ArrayList;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.awt.*;
import java.util.Collections;

import javafx.event.ActionEvent;

import javafx.geometry.Insets;

public class BlackjackGame extends Application {
	Text topLabel, typeMoneyAmount, typeBetAmount, moreCards,moreRounds, cardInfo, curTotal, curMoney;
	BorderPane  borderPane;
	TextField moneyAmount, betAmount;
	VBox v1, v2, v3;
	HBox h1,h2, h3, h4,h5,h6,h7,h8, h9,h10,h11;
	Button startGame, yesCard, noCard, yesRound, noRound;

	ArrayList<Card> playerHand=new ArrayList<Card>();
	ArrayList<Card> bankerHand=new ArrayList<Card>();
	BlackjackDealer theDealer=new BlackjackDealer();
	BlackjackGameLogic gameLogic=new BlackjackGameLogic();
	double currentBet; // the amount currently bet from the user
	double totalWinnings; // the total amount of value that the user has

	public double evaluateWinnings(ArrayList<Card> playerHand, ArrayList<Card> dealerHand, double currentBet) {

		String winner = gameLogic.whoWon(playerHand, dealerHand);

		if (winner.equals("Player")) {
			// Player wins, return double the bet
			return currentBet * 2;
		} else if (winner.equals("Dealer")) {
			// Dealer wins, player loses the bet
			return -currentBet;
		} else {
			// Push, player gets back the bet amount
			return currentBet;
		}
	}

	public String displayCard(Card card){
		String result="";
		if(card.value==1){
			result+="Ace";
		}
		else if(card.value==11){
			result+="Jack";
		}
		else if(card.value==12){
			result+="Queen";
		}
		else if(card.value==13){
			result+="King";
		}
		else{
			result+=String.valueOf(card.value);
		}
		result+=" of "+card.suit;

		return result;
	}

	class Card {
		private String suit;
		private int value;

		public Card(String theSuit, int theValue) {
			suit = theSuit;
			value = theValue;
		}

		public int getValue() {
			return value;
		}


	}
	class BlackjackDealer {
		private ArrayList<Card> deck;

		public BlackjackDealer() {
			deck = new ArrayList<>();
			generateDeck();
			shuffleDeck();
		}

		public void generateDeck() {
			deck.clear(); // Clear the deck in case it's not empty
			String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
			for (String suit : suits) {
				for (int value = 1; value <= 13; value++) {
					deck.add(new Card(suit, value));
				}
			}
		}

		public ArrayList<Card> dealHand() {
			ArrayList<Card> hand = new ArrayList<>();
			// Draw the first card for the hand
			hand.add(deck.remove(0));
			// Draw the second card for the hand
			hand.add(deck.remove(0));
			return hand;
		}
		public Card drawOne(){
			return deck.remove(0);
		}

		public void shuffleDeck() {
			Collections.shuffle(deck);

		}

		public int deckSize() {
			return deck.size();
		}
	}
	class BlackjackGameLogic {

		public String whoWon(ArrayList<Card> playerHand, ArrayList<Card> dealerHand) {
			int playerTotal = handTotal(playerHand);
			int dealerTotal = handTotal(dealerHand);

			if (playerTotal > 21) {
				return "Dealer";
			} else if (dealerTotal > 21) {
				return "Player";
			} else if (playerTotal == 21) {
				return "Player";
			} else if (dealerTotal == 21) {
				return "Dealer";
			} else if (playerTotal > dealerTotal) {
				return "Player";
			} else if (playerTotal < dealerTotal) {
				return "Dealer";
			} else {
				return "Push";
			}
		}

		public int handTotal(ArrayList<Card> hand) {
			int total = 0;
			int numAces = 0;

			for (Card card : hand) {
				int value = card.getValue();
				if (value == 1) {
					numAces++;
					total += 11; // Assume ace as 11 first
				} else if (value > 10) {
					total += 10; // Face cards are worth 10
				} else {
					total += value;
				}
			}

			// Adjust total for aces if needed
			while (total > 21 && numAces > 0) {
				total -= 10; // Change ace value from 11 to 1
				numAces--;
			}

			return total;
		}

		public boolean evaluateBankerDraw(ArrayList<Card> hand) {
			return handTotal(hand) <= 16;
		}
	}

	//to keep track of current money
	public double setMoney(double money, ArrayList<Double> bets){
		for(double num:bets){
			money+=num;
		}
		return money;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {


		primaryStage.setTitle("Yana Ninovska Project2");

		borderPane= new BorderPane();
		topLabel=new Text("Let's play BlackJack!");
		topLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		typeMoneyAmount=new Text("Enter starting amount of money");
		typeBetAmount=new Text("Enter starting amount of bet");
		moneyAmount=new TextField();
		moneyAmount.setPromptText("Enter amount $");
		betAmount=new TextField();
		betAmount.setPromptText("Enter amount $");
		startGame = new Button("Start Game!");
		startGame.setStyle("-fx-font-size: 18px;-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF; -fx-background-radius: 10;");
		startGame.setPadding(new Insets(20));
		moreCards=new Text("Do you want to get more cards?");
		moreRounds=new Text("Do you want to play one more round?");
		//moreCards.setTextAlignment(Pos.CENTER);
		yesCard=new Button("Yes");
		noCard=new Button("No");
		yesCard.setStyle("-fx-font-size: 15px;-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF; -fx-background-radius: 10;");
		noCard.setStyle("-fx-font-size: 15px;-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF; -fx-background-radius: 10;");
		cardInfo=new Text();
		cardInfo.setStyle("-fx-font-size: 15px;");
		yesRound=new Button("Yes");
		noRound=new Button("No");
		yesRound.setStyle("-fx-font-size: 15px;-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF; -fx-background-radius: 10;");
		noRound.setStyle("-fx-font-size: 15px;-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF; -fx-background-radius: 10;");
		curTotal=new Text("Your Total: ");
		curMoney=new Text("Your Money: ");

		h1=new HBox(topLabel);
		h1.setAlignment(Pos.CENTER);
		h2=new HBox(cardInfo);
		h2.setAlignment(Pos.TOP_CENTER);
		h2.setPadding(new Insets(20));
		borderPane.setTop(h1);
		borderPane.setCenter(h2);

		h3=new HBox(10,typeMoneyAmount, moneyAmount);
		h3.setAlignment(Pos.CENTER);
		h4=new HBox(10,typeBetAmount, betAmount);
		h5=new HBox(startGame);
		h5.setAlignment(Pos.CENTER);
		h6=new HBox(25,curTotal, curMoney);
		h6.setAlignment(Pos.CENTER);
		v1=new VBox(10,h3,h4,h5,h6);
		borderPane.setRight(v1);
		v1.setPadding(new Insets(30));
		v1.setSpacing(20);

		h7=new HBox(moreCards);
		h7.setAlignment(Pos.CENTER);
		h8=new HBox(20, yesCard, noCard);
		h8.setAlignment(Pos.CENTER);
		v2=new VBox(10,h7,h8);
		v2.setPadding(new Insets(30));
		v2.setSpacing(20);

		h9=new HBox(moreRounds);
		h9.setAlignment(Pos.CENTER);
		h10=new HBox(20, yesRound, noRound);
		h10.setAlignment(Pos.CENTER);
		v3=new VBox(10,h9,h10);
		v3.setPadding(new Insets(30));
		v3.setSpacing(20);
		v3.setVisible(false);
		h11=new HBox(20,v2,v3);
		h11.setAlignment(Pos.CENTER);
		borderPane.setBottom(h11);
		h6.setVisible(false);
		h11.setVisible(false);


		 double[] money = {0.0};
		double[] startingMoney = {0.0};
		ArrayList<Double> bets = new ArrayList<>();




		startGame.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent action){
				//cardInfo.setText("");
				if (moneyAmount.getText().isEmpty() ||betAmount.getText().isEmpty()){
					topLabel.setText("Please Enter Money and Bet Amounts");
					return;
				}
				else if (  Integer.parseInt(betAmount.getText())> Integer.parseInt(moneyAmount.getText()) ) {
					topLabel.setText("Bet amount cannot be bigger that Money amount");
					return;
				}
				currentBet =Double.parseDouble(betAmount.getText());
				startingMoney[0] =Double.parseDouble(moneyAmount.getText());
				money[0]=setMoney(startingMoney[0],bets);
				topLabel.setText("Playing BlackJack");
				h3.setVisible(false);
				h4.setVisible(false);
				h5.setVisible(false);
				h6.setVisible(true);
				h11.setVisible(true);
				v2.setVisible(true);
				v3.setVisible(false);

				playerHand=theDealer.dealHand();
				bankerHand=theDealer.dealHand();
				for( Card card:playerHand){
					cardInfo.setText(cardInfo.getText()+ displayCard(card)+"\n");
				}
				totalWinnings=gameLogic.handTotal(playerHand);
				curTotal.setText("Your Total: "+ totalWinnings);
				curMoney.setText("Your Money: "+ money[0]);

				boolean paneChange1=false;
				double amount =evaluateWinnings(playerHand,bankerHand,currentBet);
				if(gameLogic.handTotal(playerHand)==21.0 && gameLogic.handTotal(bankerHand)!=21.0){
					topLabel.setText("Player hit BlackJack!");
					cardInfo.setText(cardInfo.getText()+ "\n"+"You won "+ String.valueOf(amount) );
					paneChange1=true;
				}
				else if (gameLogic.handTotal(playerHand)==21.0 && gameLogic.handTotal(bankerHand)==21.0) {
					topLabel.setText("It's a draw");

					cardInfo.setText(cardInfo.getText()+ "\n"+"You get your bet back "+ String.valueOf(amount) );
					paneChange1=true;
				}
				else if (gameLogic.handTotal(playerHand)!=21.0 && gameLogic.handTotal(bankerHand)==21.0) {
					topLabel.setText("Dealer hit BlackJack!");
					cardInfo.setText(cardInfo.getText()+ "\n"+"You Lost "+ String.valueOf(amount) );
					paneChange1=true;
				}
				if(paneChange1){
					bets.add(amount);
					money[0]=setMoney(startingMoney[0],bets);
					v2.setVisible(false);//no more cards q
					v3.setVisible(true);//one more round q
					typeBetAmount.setText("Enter new bet amount if you want");
					h4.setVisible(true);//for new bet
					curMoney.setText("Your Money: "+ money[0]);
				}

			}

		});

		yesCard.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent action){
				playerHand.add(theDealer.drawOne());
				cardInfo.setText("");
				for( Card card:playerHand) {
					cardInfo.setText(cardInfo.getText() + displayCard(card) + "\n");
				}
				totalWinnings=gameLogic.handTotal(playerHand);
				curTotal.setText("Your Total: "+ totalWinnings);
				boolean paneChange2=false;
				double amount =evaluateWinnings(playerHand,bankerHand,currentBet);


				if(gameLogic.handTotal(playerHand)==21.0){
					topLabel.setText("Player hit 21 and won!");
					cardInfo.setText(cardInfo.getText()+ "\n"+"You won "+ String.valueOf(amount) );
					paneChange2=true;
				}
				else if (gameLogic.handTotal(playerHand)>21.0) {
					topLabel.setText("Player got over 21 and lost!");
					cardInfo.setText(cardInfo.getText()+"\n"+ "You lost "+ String.valueOf(amount) );
					paneChange2=true;
				}
				else{
					if(gameLogic.evaluateBankerDraw(bankerHand)){
						bankerHand.add(theDealer.drawOne());
					}

					if(gameLogic.handTotal(bankerHand)==21.0){
						topLabel.setText("Dealer hit 21 and won!");
						cardInfo.setText(cardInfo.getText()+ "\n"+"You Lost "+ String.valueOf(amount) );
						paneChange2=true;
					}
					else if (gameLogic.handTotal(bankerHand)>21.0){
						topLabel.setText("Dealer got over 21 and lost!");
						cardInfo.setText(cardInfo.getText()+ "\n"+"You won "+ String.valueOf(amount) );
						paneChange2=true;
					}

				}
				if(paneChange2){
					bets.add(amount);
					money[0]=setMoney(startingMoney[0],bets);
					v2.setVisible(false);//no more cards q
					v3.setVisible(true);//one more round q
					typeBetAmount.setText("Enter new bet amount if you want");
					h4.setVisible(true);//for new bet
					curMoney.setText("Your Money: "+ money[0]);
				}
			}

		});

		noCard.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent action){
				String winner=gameLogic.whoWon(playerHand,bankerHand);
				double amount =evaluateWinnings(playerHand,bankerHand,currentBet);
				if(winner.equals("Player")){
					topLabel.setText("Player Won!");
					cardInfo.setText(cardInfo.getText()+ "\n"+"You won "+ String.valueOf(amount) );
				}
				else if (winner.equals("Dealer")) {
					topLabel.setText("Dealer Won!");
					cardInfo.setText(cardInfo.getText()+ "\n"+"You lost "+ String.valueOf(amount) );
				}
				else{
					topLabel.setText("It's a draw");
					cardInfo.setText(cardInfo.getText()+ "\n"+"You get your bet back "+ String.valueOf(amount) );
				}

				bets.add(amount);
				money[0]=setMoney(startingMoney[0],bets);
				v2.setVisible(false);//no more cards q
				v3.setVisible(true);//one more round q
				typeBetAmount.setText("Enter new bet amount if you want");
				h4.setVisible(true);//for new bet
				curMoney.setText("Your Money: "+ money[0]);
			}

		});

		noRound.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent action){
				topLabel.setText("Let's play BlackJack!");
				h3.setVisible(true);
				h4.setVisible(true);
				h5.setVisible(true);
				h6.setVisible(false);
				h11.setVisible(false);
				typeBetAmount.setText("Enter starting amount of bet");
				cardInfo.setText("");
				moneyAmount.setText("");
				betAmount.setText("");
				BlackjackDealer theDealer=new BlackjackDealer();
			}

		});
		yesRound.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent action){

				double newBet =Double.parseDouble(betAmount.getText());
				if (newBet>money[0]){
					topLabel.setText("Bet amount is too big");
				}
				else {
					BlackjackDealer theDealer=new BlackjackDealer();
					//moneyAmount.setText(String.valueOf(money[0]));
					cardInfo.setText("");
					startGame.fire();
				}


			}

		});




		Scene scene = new Scene(borderPane, 700,600);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}


