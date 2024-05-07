
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CheckFile {

    private static final String USER_FILE_PATH = "user.txt";
    private static final String QUES_FILE_PATH = "question.txt";

    private static final String TRAN_FILE_PATH = "transactionHistory.txt";
    private static final String PROD_FILE_PATH = "product.txt";
    private static final String DELIMITER = ",";
    private static final String USER_INITIAL_DATA = "admin1,123456,Admin 1,null,null,true,0,0,7,null\n"
            + "admin2,1234566,Admin 2,null,null,true,0,0,7,null\n"
            + "admin3,abc123,Admin 3,null,null,true,0,0,7,null\n";

    private static final String PROD_INITIAL_DATA = "1001,$5 Coupon,Can be used in any merchant, 5, 100\n"
            + "1002,$10 Coupon,Can be used in any merchant, 10, 100\n"
            + "1003,$20 Coupon,Can be used in any merchant, 20, 100\n";

    private static final String QUES_INITIAL_DATA = "1,1.What time do you usually go to bed? ,(A) 10pm (B) 11pm (C) 12am (D) >12am"
            + ",2.How many hours do you usually sleep? ,(A) less than 8 hours (B) 8 hours (C) 9 hours (D) more than 9 hours"
            + ",3.How often you play with your phone before sleep? ,(A) everyday (B) often (C) occasionally (D) never"
            + ",4.Which type of website do you often use?,(A) E-commerce (B) Short video (C) Movie (D) Other"
            + ",5.How often you watch a movie or a TV series? ,(A) Everyday (B) Often (C) Occasionally (D) Never"
            + ",6.Will you pay for watching a movie online?, (A) Yes (B) Maybe (C) Not sure (D) No"
            + ",7.Are you a movie fan or a TV series fan?, (A) Exactly movie fan (B) Exactly TV series fan (C) Both (D) Neither"
            + ",8.What is the duration of movie you recommended?, (A) less than 1 hour 30 minutes  (B) 1 hour 30 minutes (C) 2 hours (D) more than 2 hours"
            + ",9.What is the duration of an episode of a TV series you recommended?, (A) 20~30 minutes (B) 30~40 minutes (C) 40~50 minutes (D) more than 50 minutes"
            + ",10.Do you hate the advestisment placement in a movie or a TV series?, (A) Exactly Yes (B) A bit dislike (C) Neutral (D) Nope\n"
            + "2,1.What is your age range? ,(A) less than 18 years old (B) 18~29 years old (C) 30~40 years old (D) more than 40 years old"
            + ",2.What is your gender? ,(A) Male (B) Female (C) Not sure (D) Secret"
            + ",3.On average that how many glasses of water do you drink in a day? ,(A) 1-2 glasses (B) 3-4 glasses (C) 5-6 glasses (D) 7 or more glasses"
            + ",4.How frequently do you engage in exercise or physical activity per week? ,(A) Never (B) 1~2 times per week (C) 3~4 times per week (D) 5 or more times per week"
            + ",5.Do you have any specific reasons or goals for exercising or being physically active? ,(A) Weight loss (B) General health maintenance (C) Stress relief (D) Building muscle strength"
            + ",6.How would you describe the intensity of your typical exercise or physical activity sessions? ,(A) Low intensity (e.g. walking or yoga) (B) Moderate intensity (e.g. jogging or cycling) (C) High intensity (e.g. running or weightlifting) (D) I don't engage in exercise or physical activity"
            + ",7.What types of exercises or physical activities do you typically participate in? ,(A) Cardiovascular exercises (e.g. running or cycling) (B) Strength training (e.g. weightlifting or resistance exercises) (C) Flexibility exercises (e.g. yoga or stretching) (D) Team sports (e.g. soccer or basketball)"
            + ",8.How would you rate your overall level of hydration throughout the day? ,(A) Very hydrated (B) Moderately hydrated (C) Occasionally dehydrated (D) Frequently dehydrated"
            + ",9.Are there any barriers or challenges that prevent you from drinking more water or exercising regularly? ,(A) Lack of time (B) Lack of motivation (C) Physical limitations or health concerns (D) Lack of access to facilities or equipment"
            + ",10.Would you be interested in receiving information or resources on how to improve your hydration habits or incorporate more physical activity into your routine? ,(A) Yes (B) Intermediate (C) No (D)Maybe\n"
            + "3,1.How frequently do you use social media platforms? ,(A) Multiple times a day (B) Once a day (C) A few times a week (D) Once a week"
            + ",2.Which social media platforms do you use regularly? ,(A) Facebook (B) Instagram (C) Twitter (D) Snapchat"
            + ",3.How much time(on average) do you spend on social media each day? ,(A) less than 1 hour (B) 1~2 hours (C) 2~4 hours (D) More than 4 hours"
            + ",4.What do you primarily use social media for? ,(A) Keeping in touch with friends and family (B) Sharing updates about my life (C) Following news and current events (D) Entertainment (e.g. watching videos or memes)"
            + ",5.How do you feel about the impact of social media on your mental well-being? ,(A) Positive impact (B) Negative impact (C) No significant impact (D) I'm not sure"
            + ",6.Have you ever taken a break from social media? If so and why? ,(A) Yes and to reduce stress or anxiety (B) Yes and to focus on other activities or hobbies (C) Yes and due to concerns about privacy or security (D) No and I've never taken a break from social media"
            + ",7.How important is privacy and data security to you when using social media platforms? ,(A) Very important (B) Somewhat important (C) Not very important (D) Not important at all"
            + ",8.Do you follow any influencers or celebrities on social media? If yes and why do you follow them? ,(A) Yes and for entertainment (B) Yes and for inspiration or motivation (C) Yes and for product recommendations (D) No and I don't follow influencers or celebrities"
            + ",9.Would you prefer to see more or less advertising on social media platforms? ,(A) More advertising (B) Less advertising (C) No preference (D)Intermediate"
            + ",10.How likely are you to trust information or news shared on social media? ,(A) Very likely (B) Somewhat likely (C) Neutral (D) Unlike\n"
            + "4,1.What is the capital of Malaysia? ,(A) Kuala Lumpur (B) Putrajaya (C) Labuan (D) Singapore"
            + ",2.What is the capital of China? ,(A) Guangzhou (B) Shanghai (C) Beijing (D) Shenzhen"
            + ",3.What is the capital of Germany? ,(A) Hamburg (B) Frankfurt (C) Munich (D) Berlin"
            + ",4.What is the capital of France? ,(A) Le Mans (B) Paris (C) Nantes (D) Lyon"
            + ",5.What is the capital of Vietnam? ,(A) Ho Chi Minh City (B) Hanoi (C) Dalat (D) Vinh"
            + ",6.What is the capital of Japan? ,(A) Tokyo (B) Osaka (C) Sapporo (D) Nagoya"
            + ",7.What is the capital of Korea? ,(A) Jeju-Do (B) Busan (C) Jeonju (D) Seoul"
            + ",8.What is the capital of Australia? ,(A) Sydney (B) Melbourne (C) Perth (D) Canberra"
            + ",9.What is the capital of USA? ,(A) Los Angeles (B) Washington (C) New York (D) Boston"
            + ",10.What is the capital of Italy? ,(A) Milan (B) Venice (C) Rome (D) Florence\n"
            + "5,1.What is the capital of Malaysia? ,(A) Kuala Lumpur (B) Putrajaya (C) Labuan (D) Singapore"
            + ",2.What is the capital of China? ,(A) Guangzhou (B) Shanghai (C) Beijing (D) Shenzhen"
            + ",3.What is the capital of Germany? ,(A) Hamburg (B) Frankfurt (C) Munich (D) Berlin"
            + ",4.What is the capital of France? ,(A) Le Mans (B) Paris (C) Nantes (D) Lyon"
            + ",5.What is the capital of Vietnam? ,(A) Ho Chi Minh City (B) Hanoi (C) Dalat (D) Vinh"
            + ",6.What is the capital of Japan? ,(A) Tokyo (B) Osaka (C) Sapporo (D) Nagoya"
            + ",7.What is the capital of Korea? ,(A) Jeju-Do (B) Busan (C) Jeonju (D) Seoul"
            + ",8.What is the capital of Australia? ,(A) Sydney (B) Melbourne (C) Perth (D) Canberra"
            + ",9.What is the capital of USA? ,(A) Los Angeles (B) Washington (C) New York (D) Boston"
            + ",10.What is the capital of Italy? ,(A) Milan (B) Venice (C) Rome (D) Florence\n"
            + "201,1.What is something that brings you joy or makes you smile? ,"
            + ",2. Can you share a favorite memory or experience from your life?,"
            + ",3. What is a skill or hobby you enjoy practicing in your free time?,"
            + ",4. Describe a place you've always wanted to visit or explore. ,"
            + ",5. Who has been a positive influence or role model in your life and why?,"
            + ",6. What is a goal or aspiration you hope to achieve in the future? ,"
            + ",7. Share a piece of advice or wisdom that has resonated with you.,"
            + ",8. Reflect on a time when you overcame a challenge or obstacle.,"
            + ",9. What is something you're grateful for in your life right now?,"
            + ",10. Is there a quote or motto that inspires you or holds special meaning for you?,\n"
            + "202,1. What is one thing you're passionate about and why?,"
            + ",2. Can you share a favorite book | movie or song and what it means to you?,"
            + ",3. Describe a moment when you felt proud of yourself or accomplished.,"
            + ",4. What is a dream you've always had but haven't yet pursued? ,"
            + ",5. Share a random act of kindness you've either given or received.,"
            + ",6. Reflect on a lesson you've learned from a difficult experience.,"
            + ",7. What is something you wish more people knew or understood about you?,"
            + ",8. If you could travel back in time, what advice would you give your younger self?,"
            + ",9. Describe a small gesture that someone did for you that made a big impact.,"
            + ",10. What is something you're looking forward to in the future?,\n"
            + "101,1. Do you feel satisfied with your current job?,(T) True  (F) False"
            + ",2. Do you have a good work-life balance?,(T) True  (F) False"
            + ",3. Do you feel supported by your colleagues?,(T) True  (F) False"
            + ",4. Is your workload manageable? ,(T) True  (F) False"
            + ",5. Do you feel appreciated by your supervisor?,(T) True  (F) False"
            + ",6. Do you have opportunities for career growth in your current position?,(T) True  (F) False"
            + ",7. Are you motivated to perform well at work?,(T) True  (F) False"
            + ",8. Are you comfortable expressing your opinions and ideas at work?,(T) True  (F) False"
            + ",9. Do you receive adequate training and support to fulfill your job responsibilities?,(T) True  (F) False"
            + ",10. Overall, do you feel happy with your professional life?,(T) True  (F) False\n"
            + "102,1. Do you feel adequately compensated for your work?,(T) True  (F) False"
            + ",2. Do you feel respected by your peers at work?,(T) True  (F) False"
            + ",3. Do you have clear expectations for your job responsibilities?,(T) True  (F) False"
            + ",4. Do you feel your workplace promotes diversity and inclusion? ,(T) True  (F) False"
            + ",5. Do you feel your opinions are valued in decision-making processes?,(T) True  (F) False"
            + ",6. Do you receive regular feedback on your performance?,(T) True  (F) False"
            + ",7. Do you feel your work contributes to the overall goals of the organization?,(T) True  (F) False"
            + ",8. Do you have access to necessary resources to do your job effectively?,(T) True  (F) False"
            + ",9. Do you feel your company prioritizes employee well-being?,(T) True  (F) False"
            + ",10. Do you see opportunities for advancement within your organization?,(T) True  (F) False\n";

//check user file, if file not exist and create file only return true, else will be false
    public static boolean checkUserFile() {
        File file = new File(USER_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                // Write initial data to the user file
                writeInitialUserData();
                return true;
            } catch (IOException e) {
                System.out.println("Unexpected Error in User file creation, Please contact administrator: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

    public static boolean checkProdFile() {
        File file = new File(PROD_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                // Write initial data to the user file
                writeInitialProdData();
                return true;
            } catch (IOException e) {
                System.out.println("Unexpected Error in User file creation, Please contact administrator: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

    public static boolean checkQuesFile() {
        File file = new File(QUES_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                // Write initial data to the user file
                writeInitialQuesData();
                return true;
            } catch (IOException e) {
                System.out.println("Unexpected Error in User file creation, Please contact administrator: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

    private static void writeInitialUserData() {
        try (FileWriter writer = new FileWriter(USER_FILE_PATH)) {
            writer.write(USER_INITIAL_DATA);
        } catch (IOException e) {
            System.out.println("Error writing initial data to the user file: " + e.getMessage());
        }
    }

    private static void writeInitialProdData() {
        try (FileWriter writer = new FileWriter(PROD_FILE_PATH)) {
            writer.write(PROD_INITIAL_DATA);
        } catch (IOException e) {
            System.out.println("Error writing initial data to the user file: " + e.getMessage());
        }
    }

    private static void writeInitialQuesData() {
        try (FileWriter writer = new FileWriter(QUES_FILE_PATH)) {
            writer.write(QUES_INITIAL_DATA);
        } catch (IOException e) {
            System.out.println("Error writing initial data to the user file: " + e.getMessage());
        }
    }

    public static boolean checkTransactionFile() {
        File file = new File(TRAN_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return true;
            } catch (IOException e) {
                System.out.println("Unexpected Error in Transaction History file creation, Please contact administrator: " + e.getMessage());
            }
        }
        return true;
    }
}
