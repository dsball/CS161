//P1
//Daniel Ball
//CS 161
//1/27/2014

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class P1 {
	
//Class for easily maintaining word list/count, dynamically sized array
	private class WordArray
	{
		private class Word
		{
			int count = 0;
			String word = "";
		}
		
		private int nextEmpty = 0;
		private Word[] wordList = new Word[10]; 

		public void test()
		{
			for(int i = 0 ; i < wordList.length ; i++)
			{
				wordList[i].count = i;
				wordList[i].word = Character.toString((char) i) ;
			}
		}
		private void grow()
		{
			Word[] temp = new Word[wordList.length*2];
			for(int i = 0 ; i < wordList.length; i++)
			{
				temp[i] = wordList[i];
			}
			wordList = temp;
		}
		
		public void addWord(String word)
		{
			for(int i = 0 ; i <= nextEmpty ; i++)
			{
				if(word.equals(wordList[i].word))
				{
					wordList[i].count++;
				}
				else if(i == nextEmpty)
				{
					nextEmpty++;
					wordList[nextEmpty].word = word;
					wordList[nextEmpty].count ++;
				}
			}
			if(nextEmpty == wordList.length)
			{
				this.grow();
			}
		}
		
		public String mostCommon()
		{
			int highest = 0;
			for(int i = 0; i< nextEmpty ; i++)
			{
				if(wordList[i].count > wordList[highest].count)
				{
					highest = i;
				}
			}
			return wordList[highest].word;
		}
		
	}
	
	
	
	private static void printArray(String[] array)
	{
		
		System.out.println("Length: " + array.length);
		for(int i = 0 ; i< array.length ; i++)
		{
			System.out.println(i + ": " + array[i]);
		}
	}
	
	String[] readTweets(String fileName)
	{
		String tweets = "";
		String[] tweetArray = null;
		try
		{
			Scanner tweetFile = new Scanner(new File(fileName));
			while(tweetFile.hasNextLine())
			{
				tweets += (tweetFile.nextLine() + "\n");
			}
			tweetFile.close();
		} catch (IOException e)
		{
			System.out.println("Error reading tweet file!");
			System.exit(0);
		}
		String[] fullArray = tweets.split("\t|\n");
		
		tweetArray = new String[fullArray.length/3];
		for(int i = 0, j = 0; i<fullArray.length ; i+=3, j++)
		{
			tweetArray[j] = fullArray[i+2];
		}	
		return tweetArray;
	}
	
	public String[] readStopWords(String fileName)
	{
		String words = "";
		try
		{
			Scanner wordFile = new Scanner(new File(fileName));
			while(wordFile.hasNextLine())
			{
				words += (wordFile.nextLine() + " ");
			}
			wordFile.close();
		} catch (IOException e)
		{
			System.out.println("Error reading tweet file!");
			System.exit(0);
		}
		return words.split("\\s+");
	}

	public String mostCommonWord(String[] tweets)
	{
		WordArray wordList = new WordArray();
		String[] splitTweet = null;
		
		for(int i = 0; i < tweets.length ; i++)
		{
			splitTweet = tweets[i].split("(\\.|\\s|\\*|\\,|\\!|\")+");
			
			for(int j = 0 ; j< splitTweet.length ; j++)
			{
				//wordList.addWord(splitTweet[j]);
				wordList.test();
			}
			
		}
		
		return wordList.mostCommon();
	}
	
//	public String mostCommonWordExcludingStopWords(String[] tweets)
//	{
//		
//	}
	
	public static void main(String[] args) 
	{
		
		P1 tweetCheck = new P1();
		String[] tweetArray = tweetCheck.readTweets(args[0]);
		String[] wordArray = tweetCheck.readStopWords(args[1]);
		System.out.println(tweetCheck.mostCommonWord(tweetArray));
	}

}
