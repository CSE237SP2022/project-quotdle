package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import quotdle.AnswerGenerator;

class AnswerGeneratorTests {
	String[] possibleWordleAnswers = {"aargh", "aback", "abaft", "abaft", "aboon", "aboon", "about", "about", "above", "above", "above", "abuse", "accel", "acute", "adieu", "adios", "admit", "adopt", "adown", "adown", "adult", "afoot", "afore", "afore", "afore", "afoul", "after", "after", "after", "again", "agape", "agent", "agogo", "agone", "agree", "ahead", "ahull", "alack", "alcon", "alife", "alike", "aline", "alive", "allow", "aloft", "aloha", "alone", "alone", "along", "along", "aloof", "aloof", "aloud", "alter", "amiss", "among", "amply", "amuck", "anger", "angry", "apace", "apart", "apple", "apply", "aptly", "arear", "argue", "arise", "aside", "askew", "aught", "avast", "avoid", "award", "aware", "awful", "awful", "badly", "bakaw", "bally", "basic", "basis", "basta", "beach", "begad", "begin", "below", "below", "birth", "black", "blame", "bless", "blige", "blind", "block", "blood", "board", "bothe", "brain", "brava", "brave", "bravo", "bread", "break", "break", "brief", "bring", "bring", "broad", "brown", "brown", "build", "burst", "buyer", "canny", "carry", "catch", "cause", "cause", "chain", "chair", "cheap", "cheap", "check", "chest", "chief", "chief", "child", "china", "chook", "circa", "civil", "claim", "claim", "class", "clean", "clean", "clean", "clear", "clear", "clear", "climb", "clock", "close", "close", "coach", "coast", "count", "court", "cover", "cover", "coyly", "crazy", "cream", "crime", "cross", "cross", "cross", "crowd", "crown", "cycle", "daily", "daily", "damme", "dance", "dance", "death", "depth", "dildo", "dimly", "dirty", "dirty", "ditto", "ditto", "doubt", "doubt", "draft", "drama", "dream", "dress", "drily", "drink", "drink", "drive", "drive", "dryly", "dully", "early", "early", "earth", "empty", "enemy", "enjoy", "enter", "entry", "equal", "error", "event", "exact", "exist", "extra", "extra", "faint", "faith", "false", "fatly", "fault", "feyly", "field", "fifth", "fight", "fight", "final", "final", "first", "first", "fitly", "floor", "focus", "focus", "force", "force", "forte", "forth", "frame", "frank", "fresh", "fresh", "frick", "front", "front", "fruit", "fudge", "fully", "funny", "funny", "furth", "gaily", "gayly", "giant", "glass", "godly", "golly", "grand", "grant", "grass", "gratz", "great", "great", "green", "green", "gross", "group", "guess", "guide", "hallo", "haply", "happy", "harsh", "hasta", "havoc", "heart", "heavy", "heavy", "hella", "hella", "hella", "hello", "hence", "henry", "horse", "hotel", "hotly", "house", "howay", "howdy", "hullo", "human", "huzza", "icily", "ideal", "image", "imply", "index", "infra", "inner", "input", "intl.", "issue", "issue", "japan", "jesus", "jildi", "joint", "jolly", "jones", "judge", "judge", "kapow", "knife", "large", "laugh", "laura", "laxly", "layer", "learn", "leave", "legal", "lento", "let’s", "level", "level", "lewis", "light", "light", "light", "limit", "limit", "local", "loose", "loose", "lordy", "lowly", "lucky", "lunch", "madly", "magic", "major", "major", "march", "marry", "marry", "match", "match", "maybe", "mercy", "metal", "minor", "minus", "model", "money", "month", "moral", "motor", "mouth", "music", "naked", "nasty", "naval", "neath", "never", "newly", "night", "night", "nobly", "noise", "north", "novel", "nurse", "occur", "oddly", "offer", "offer", "often", "one’s", "order", "order", "other", "other", "other", "other", "ought", "ought", "outer", "owner", "panel", "paper", "party", "party", "peace", "peter", "phase", "phone", "phone", "piano", "piece", "pilot", "pitch", "place", "place", "plain", "plain", "plane", "plant", "plate", "plonk", "plonk", "plumb", "point", "point", "pound", "power", "press", "press", "price", "pride", "prime", "prior", "prior", "prize", "proof", "proud", "prove", "psych", "queen", "queer", "quick", "quick", "quiet", "quite", "quite", "radio", "raise", "ramen", "range", "rapid", "rapid", "ratio", "reach", "ready", "redly", "refer", "relax", "reply", "right", "right", "right", "river", "roman", "rough", "rough", "round", "round", "round", "round", "route", "royal", "rugby", "rural", "sadly", "salve", "scale", "scene", "scope", "score", "secus", "selly", "sense", "serve", "shall", "shape", "share", "share", "sharp", "sharp", "sheep", "sheer", "sheer", "sheet", "shift", "shift", "shily", "shirt", "shock", "shoot", "short", "short", "shyly", "sight", "silly", "silly", "simon", "since", "since", "since", "sixth", "skill", "skoal", "slash", "sleek", "sleep", "sleep", "slyly", "small", "small", "smart", "smile", "smith", "smoke", "sniff", "so-so", "solid", "solve", "sooey", "sorry", "sound", "sound", "sound", "south", "space", "spang", "spare", "speak", "speed", "spend", "spite", "spite", "split", "sport", "squad", "srsly", "staff", "stage", "stand", "stark", "start", "start", "state", "state", "steam", "steel", "steep", "stick", "still", "still", "stock", "stone", "stone", "store", "stour", "study", "study", "stuff", "style", "sugar", "super", "super", "sweet", "table", "tally", "tanto", "taste", "teach", "terry", "thame", "thank", "theme", "there", "there", "there", "thiam", "thick", "thick", "thine", "thine", "thing", "think", "third", "throw", "thwap", "tight", "tight", "title", "today", "tomoz", "total", "total", "touch", "touch", "tough", "tough", "tower", "track", "trade", "train", "train", "treat", "trend", "trial", "truly", "trust", "trust", "truth", "twice", "twirp", "uncle", "under", "under", "union", "unity", "until", "until", "upper", "upset", "urban", "usual", "utter", "vague", "valid", "value", "verry", "video", "viola", "visit", "visit", "vital", "vivat", "voice", "voice", "wacko", "wahey", "wanly", "waste", "waste", "watch", "watch", "water", "wetly", "where", "where", "where", "which", "while", "while", "whist", "white", "white", "whole", "whole", "whose", "whoso", "wilma", "wirra", "woman", "woops", "world", "worry", "would", "wowie", "write", "wrong", "wrong", "wryly", "yecch", "yeeha", "yeesh", "young", "yours", "yours", "youth", "yowch", "zowie", "false",};
	static AnswerGenerator answerGenerator;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void getWordleAnswerTest() {
		for(int i = 0; i < 10; ++i) {
			String wordleAnswer = answerGenerator.generateNewWordle();
			boolean contains = Arrays.stream(possibleWordleAnswers).anyMatch(wordleAnswer::equals);
			assertTrue(contains);
		}
	}
	@Test 
	void testTest() {
	}
}