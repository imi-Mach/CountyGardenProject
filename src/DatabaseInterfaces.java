

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * App is our basic admin app.  For now, all it does is connect to the database
 * and then disconnect
 */

enum InterfaceSetting {
    corporate,
    customer,
    agent,
    adjuster;
}

public class DatabaseInterfaces {

    /**
     * Print the menu for our program
     */
    static void menu(InterfaceSetting ifs) {

        switch(ifs) {
            case corporate:
                break;
            case customer:
                System.out.println("\nCustomer Overview Menu:");
                System.out.println("[P] Policy options");
                System.out.println("\t[V] View Policy\n");
                System.out.println("\t[+] Add policy");
                System.out.println("\t[-] Drop policy");
                System.out.println("\t[M] Modify policy\n");

                System.out.println("[C] Claim options");
                System.out.println("\t[V] View claim");
                System.out.println("\t[+] Add claim");
                System.out.println("\t[-] Nullify claim\n");

                System.out.println("[T] Transaction Options");
                System.out.println("\t[V] View active inquries");
                System.out.println("\t[+] Make payment\n");
                break;

            case agent:
                System.out.println("\nAgent Menu:");
                System.out.println("\t[V] View customers");
                System.out.println("\t[R] View revenu from customers");
                System.out.println("\t[?] Display overview menu");
                System.out.println("\t[q] quit interface\n");
                break;

            case adjuster:
                break;
        }

        System.out.println("General Options:");
        System.out.println("\t[?] Display overview menu");
        System.out.println("\t[q] quit interface\n");
        return;

    }

    static String printIfs(InterfaceSetting ifs) { /* TODO: Use this tool */
        switch(ifs) {
            case corporate:
                return "CORPORATE";
            case customer:
                return "CUSTOMER";
            case agent:
                return "AGENT";
            case adjuster:
                return "ADJUSTER";
        }
        System.out.println("ERROR: IFS failed to be set correctly");
        return "";
    }

    static String userInput(BufferedReader in, String prompt) {
        String result = null;
        
        System.out.print(prompt + ":> ");
        try {
            result = in.readLine();
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }

        return result;
    }

    static void printRows(List<List<String>> data) {
        /* ask how many entries to be viewed */
        if(data == null) {
            System.out.println("ERROR: Attempting to display NULL data");
            return;
        }
        int[] maxLengths = new int[data.get(0).size()];
        int rowNum = 0;

        for (List<String> row : data) {
            for (int i = 0; i < row.size(); i++) {
                maxLengths[i] = Math.max(maxLengths[i], row.get(i).length());
            }
        }

        StringBuilder formatBuilder = new StringBuilder();

        for (int maxLength : maxLengths) {
            formatBuilder.append("%-").append(maxLength + 2).append("s");
        }

        String format = formatBuilder.toString();

        StringBuilder result = new StringBuilder();

        for (List<String> row : data) {
            result.append(String.format(format, row.toArray(new String[0]))).append("\n");
            rowNum++;
        }

        System.out.println("\n"+result);

        System.out.println("Rows printed: " + (rowNum-1) + "\n"); /* don't count header */

    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char prompt(BufferedReader in, String actions) {
        // The valid actions:

        // We repeat until a valid single-character option is selected        
        while (true) {
            String action = null;

            if((action = userInput(in, "[" + actions + "]")) == null) continue;

            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("ERROR: Invalid Command.");
        }
    }


    /**
     * Ask the user to enter a String message
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The string that the user provided.  May be "".
     */
    static String getString(BufferedReader in, String message) {
        String s;
        try {
            System.out.print(message + " :> ");
            s = in.readLine();
        } catch (IOException e) {
            //e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * Ask the user to enter an integer
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The integer that the user provided.  On error, it will be -1
     */
    static int getInt(BufferedReader in, String message) {
        int i = -1;
        try {
            System.out.print(message + ":> ");
            i = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            // e.printStackTrace();
        } catch (NumberFormatException e) {
            // e.printStackTrace();
        }

        return i;
    }

    static float getFloat(BufferedReader in, String message) {
        float i = -1;
        try {
            System.out.print(message + ":> ");
            i = Float.parseFloat(in.readLine());
        } catch (IOException e) {
           // e.printStackTrace();
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }

        return i;
    }

    static InterfaceSetting pickInterface(BufferedReader in) {
        InterfaceSetting ifs = null;
        String selection = null;
        
        System.out.println("\n\n\n-------------------Interface Selection:-------------------\n");
        
        while(ifs == null) {
            
            /* Can allow user to quit here once they have tried logging in
            if(selection != null) {

            }
            */

            System.out.println("Here are the valid interface options for County Garden:");
            //System.out.println("\t[1] Corporate: Offers general corporate reports.");
            System.out.println("\t[1] Customer: Offers protocols on policies, claims, and payments");
            System.out.println("\t[2] Agent: Offers protocols on employees assigned to customers.");
            //System.out.println("\t[3] Adjuster: Offers protocols on claims and outsourcers.\n");

            System.out.println("Please enter a valid interface: 1 or 2\n");
            System.out.println("E.g:\n\t\"Interface Option:> 1\"\n");

            System.out.print("Interface Option:> ");
            if((selection = userInput(in, "Interface Option")) == null) continue;

            
            switch(selection) {
                /*case "1":
                    return InterfaceSetting.corporate;*/
                case "1":
                    return InterfaceSetting.customer;
                case "2":
                    return InterfaceSetting.agent;
                case "3":
                    return InterfaceSetting.adjuster;
                default:
                    System.out.println("ERROR: Invalid interface option! Please try again!\n");
            }

        }
        return null; /* only return null if quit */
    }

    static int customerSignIn(Database db, BufferedReader in) {

        int cus_id = -1;
        String customer_type = null;

        /* are you a new user or an old user? */
        /* if new user: insert cusotomer table with correct attributes */

        System.out.println("Please tell me if you are a new [N] or returning [R] customer.\n");

        while(true) {
            customer_type = userInput(in, "Type \"N\" or \"R\"");
            if(customer_type == null) return -1;
            else if(!customer_type.toUpperCase().equals("N") && !customer_type.toUpperCase().equals("R")) {
                System.out.println("\nERROR: Please type \"N\" if new or \"R\" if returning customer!\n");
                continue;
            }
            /*
                TODO: check if R is selected, and if there aren't any valid ids, then force N user
            */
            break;
        }

        if(customer_type.toUpperCase().equals("N")) {
            System.out.println("As a new user, County Garden requires you provide the following:");
            
            while(true) {
                String fname = null;
                String lname = null;
                String street = null;
                String city = null;
                String state = null;
                String payment = null;

                if((fname   = userInput(in, "[1] First Name")) == null) continue;
                if((lname   = userInput(in, "[2] Last Name")) == null) continue;
                if((street  = userInput(in, "[3] Street")) == null) continue;
                if((city    = userInput(in, "[4] City")) == null) continue;
                if((state   = userInput(in, "[5] State")) == null) continue;

                System.out.println("\nFinally, we need your payment method. We accept three options:");

                while(true) {

                    System.out.println("\n[1] Credit card\n[2] Debit card\n[3] Direct debit\n");
                    System.out.println("Type the corresponding method you would like to use:\n");

                    if((payment = userInput(in, "[6] Default Payment Method")) == null) continue;
                    else if(payment.equals("1")) {
                        payment = "CREDIT CARD";
                        break;
                    }
                    else if(payment.equals("2")) {
                        payment = "DEBIT CARD";
                        break;
                    }
                    else if(payment.equals("3")) {
                        payment = "DIRECT CARD";
                        break;
                    }
                    else {
                        System.out.println("ERROR: Please input a valid payment method!\n");
                        continue;
                    }
                }

                if((db.insertCustomer(fname, lname, street, city, state, payment)) != 1) {
                    System.out.println("ERROR: We could not add you to our system, please try again!");
                    return -1;
                }

                System.out.print("Congraduations! "+fname+" "+lname+" you are now a registered member of Country Gardens!");

                System.out.print("Next time you are asked to press \"N\" or \"R\", please type R.");
                
                return cus_id;
                
            }


        }
        else if(customer_type.toUpperCase().equals("R")) {
            System.out.println("As a returning user, please enter your customer id from the list below"); /* START HERE */

            printRows(db.getAllCustomerInfo());

            System.out.println("Please select a valid id by typing the corresponding id\n");
            
            while (true) {
                List<List<String>> custData = null;

                cus_id = getInt(in, "Customer Id Number");
                
                if(cus_id < 1) {
                    System.out.println("\nERROR: Please enter a valid input!\n");
                    continue;
                }
                else if ((custData = db.getCustomerInfo(cus_id)) == null || custData.size() == 1) {
                    System.out.println("ERROR: We could not find you in our system, please try again!");
                    return -1;
                }

                System.out.println("\nWelcome back "+custData.get(1).get(1)+" "+custData.get(1).get(2)+" to Country Gardens!");

                break;
            }

        }

        return cus_id;
    }

    static int agentSignIn(Database db, BufferedReader in) {

        int agent_id = -1;
        String customer_type = null;

        /* are you a new user or an old user? */
        /* if new user: insert cusotomer table with correct attributes */

        System.out.println("Please tell me if you are a new [N] or returning [R] agent.\n");

        while(true) {
            customer_type = userInput(in, "Type \"N\" or \"R\"");
            if(customer_type == null) return -1;
            else if(!customer_type.toUpperCase().equals("N") && !customer_type.toUpperCase().equals("R")) {
                System.out.println("\nERROR: Please type \"N\" if new or \"R\" if returning agent!\n");
                continue;
            }
            /*
                TODO: check if R is selected, and if there aren't any valid ids, then force N user
            */
            break;
        }

        if(customer_type.toUpperCase().equals("N")) {
            System.out.println("As a new user, County Garden requires you provide the following:");
            
            while(true) {
                String fname = null;
                String lname = null;
                String street = null;
                String city = null;
                String state = null;
                float payment = 50000;

                if((fname   = userInput(in, "[1] First Name")) == null) continue;
                if((lname   = userInput(in, "[2] Last Name")) == null) continue;
                if((street  = userInput(in, "[3] Street")) == null) continue;
                if((city    = userInput(in, "[4] City")) == null) continue;
                if((state   = userInput(in, "[5] State")) == null) continue;

                if((db.insertAgent(fname, lname, street, city, state, payment)) != 1) {
                    System.out.println("ERROR: We could not add you to our system, please try again!");
                    return -1;
                }

                System.out.println("Congraduations! "+fname+" "+lname+" you are now a registered agent of Country Gardens!");

                System.out.println("Next time you are asked to press \"N\" or \"R\", please type R.");
                System.out.println("Your id may not show up, but that is because there are no users");
                System.out.println("To be asigned. As users join, this will change.");
                System.out.println("(Note to tester, County Garderns will indeed assign agents as customers join)");
                
                return agent_id;
                
            }


        }
        else if(customer_type.toUpperCase().equals("R")) {
            System.out.println("As a returning user, please enter your agent id from the list below"); /* START HERE */

            printRows(db.getAllAgentInfo());

            System.out.println("Please select a valid id by typing the corresponding id\n");
            
            while (true) {
                List<List<String>> agentData = null;

                agent_id = getInt(in, "Customer Id Number");
                
                if(agent_id < 1) {
                    System.out.println("\nERROR: Please enter a valid input!\n");
                    continue;
                }
                else if ((agentData = db.getAgentInfo(agent_id)) == null || agentData.size() == 1) {
                    System.out.println("ERROR: We could not find you in our system, please try again!");
                    return -1;
                }

                System.out.println("\nWelcome back "+agentData.get(1).get(1)+" "+agentData.get(1).get(2)+" to Country Gardens!");

                break;
            }

        }

        return agent_id;
    }

    /*
    static int adjusterSignIn(Database db, BufferedReader in) {

        int adj_id = -1;
        String customer_type = null;

        System.out.println("Please tell me if you are a new [N] or returning [R] agent.\n");

        while(true) {
            customer_type = userInput(in, "Type \"N\" or \"R\"");
            if(customer_type == null) return -1;
            else if(!customer_type.toUpperCase().equals("N") && !customer_type.toUpperCase().equals("R")) {
                System.out.println("\nERROR: Please type \"N\" if new or \"R\" if returning agent!\n");
                continue;
            }
            break;
        }

        if(customer_type.toUpperCase().equals("N")) {
            System.out.println("As a new user, County Garden requires you provide the following:");
            
            while(true) {
                String fname = null;
                String lname = null;
                String street = null;
                String city = null;
                String state = null;
                float payment = 50000;

                if((fname   = userInput(in, "[1] First Name")) == null) continue;
                if((lname   = userInput(in, "[2] Last Name")) == null) continue;
                if((street  = userInput(in, "[3] Street")) == null) continue;
                if((city    = userInput(in, "[4] City")) == null) continue;
                if((state   = userInput(in, "[5] State")) == null) continue;

                if((db.insertAgent(fname, lname, street, city, state, payment)) != 1) {
                    System.out.println("ERROR: We could not add you to our system, please try again!");
                    return -1;
                }

                System.out.println("Congraduations! "+fname+" "+lname+" you are now a registered agent of Country Gardens!");

                System.out.println("Next time you are asked to press \"N\" or \"R\", please type R.");
                System.out.println("Your id may not show up, but that is because there are no users");
                System.out.println("To be asigned. As users join, this will change.");
                System.out.println("(Note to tester, County Garderns will indeed assign agents as customers join)");
                
                return agent_id;
                
            }


        }
        else if(customer_type.toUpperCase().equals("R")) {
            System.out.println("As a returning user, please enter your agent id from the list below");

            printRows(db.getAllAgentInfo());

            System.out.println("Please select a valid id by typing the corresponding id\n");
            
            while (true) {
                List<List<String>> agentData = null;

                agent_id = getInt(in, "Customer Id Number");
                
                if(agent_id < 1) {
                    System.out.println("\nERROR: Please enter a valid input!\n");
                    continue;
                }
                else if ((agentData = db.getAgentInfo(agent_id)) == null || agentData.size() == 1) {
                    System.out.println("ERROR: We could not find you in our system, please try again!");
                    return -1;
                }

                System.out.println("\nWelcome back "+agentData.get(1).get(1)+" "+agentData.get(1).get(2)+" to Country Gardens!");

                break;
            }

        }

        return agent_id;
    }
    */


    static boolean adjusterAction(Database db, BufferedReader in, int idx) {
        
        System.out.println("\nAdjuster Menu:");
        System.out.println("\t[V] View Claims");
        System.out.println("\t[?] Display overview menu");
        System.out.println("\t[q] quit interface\n");

        System.out.println("Please select one of the options above.");

        char action = prompt(in, "VR?q");

        switch (action) {

            case 'V':

                printRows(db.getAgentCustomers(idx));

                return true;

            case 'q':
                System.out.println("Quit option selected:\n\nThank you for using our services!\n");
                return false;

            case '?':
                menu(InterfaceSetting.adjuster);
                return true;

            default:
                break;
        }

        return false;
    }


    static boolean agentAction(Database db, BufferedReader in, int idx) {
        
        System.out.println("\nAgent Menu:");
        System.out.println("\t[V] View customers");
        System.out.println("\t[R] View revenu from customers");
        System.out.println("\t[?] Display overview menu");
        System.out.println("\t[q] quit interface\n");

        System.out.println("Please select one of the options above.");

        char action = prompt(in, "VR?q");

        switch (action) {

            case 'V':

                printRows(db.getAgentCustomers(idx));

                return true;
            
            case 'R':

                printRows(db.getAgentRevenu(idx));
                
                return true;

            case 'q':
                System.out.println("Quit option selected:\n\nThank you for using our services!\n");
                return false;

            case '?':
                menu(InterfaceSetting.agent);
                return true;

            default:
                break;
        }

        return false;
    }

    static boolean custAction(Database db, BufferedReader in, int idx) {

        int p_id = -1; /* for policy ids */
        int i_id = -1; /* for item ids */
        int cl_id = -1;
        int t_id = -1;
        String event = null;
        String insur_type = null;
        float baseRate = -1;
        float value = -1;
        String article = null;
        String esti_value = null;
        List<List<String>> new_policy = null;
        
        System.out.println("\nCustomer Menu:");
        System.out.println("\t[P] Policy options");
        System.out.println("\t[C] Claim options");
        System.out.println("\t[T] Transaction Options");
        System.out.println("\t[?] Display overview menu");
        System.out.println("\t[q] quit interface\n");

        System.out.println("Please select one of the options above.");

        char action = prompt(in, "PCT?q");

        switch (action) {

            case 'P':

                /* TODO: print all policies owned by the user */


                //printRows(db.getAllCustomerPolicies(idx));

                System.out.println("\nPolicy options");
                System.out.println("\t[V] View Policy");
                System.out.println("\t[+] Add policy");
                System.out.println("\t[-] Drop policy");
                System.out.println("\t[M] Modify policy");
                System.out.println("\t[?] Display overview menu");
                System.out.println("\t[q] quit interface\n");

                System.out.println("Please select one of the options above.");

                action = prompt(in, "V+-M?q");
                
                switch (action) {
                    
                    case 'V':

                        printRows(db.getAllCustomerPolicies(idx));

                        p_id = getInt(in, "Enter policy id");
                        
                        List<List<String>> policy = db.getCustomerPolicy(idx, p_id);

                        if(policy != null) printRows(policy);
                        else {
                            System.out.println("ERROR: Could not retrieve policy, may be incorrect policy id");
                        }

                        return true;
                
                    case '+':

                        System.out.println("County Garden offers four types of insurance:");

                        while(true) {

                            int insur_option = -1;

                            System.out.println("\t[1] Home    - covers home related events.");
                            System.out.println("\t\tBase Rate: $1000");
                            System.out.println("\t[2] Life    - covers a set of indiviuals in the case of death.");
                            System.out.println("\t\tBase Rate: $800");
                            System.out.println("\t[3] Health  - covers recurring medical events.");
                            System.out.println("\t\tBase Rate: $900");
                            System.out.println("\t[4] Vehicle  - covers vehicle related events.");
                            System.out.println("\t\tBase Rate: $900\n");

                            System.out.println("Please type either \"1\",\"2\",\"3\", or \"4\" for the corresponding insurance type.\n");

                            if((insur_option = getInt(in, "Insurance Option")) == -1) {
                                System.out.println("ERROR: Sadly, we do not offer the policy you entered, please try another options.");
                                continue;
                            }

                            switch(insur_option) {
                                case 1:
                                    insur_type = "HOME";
                                    baseRate = 1000;
                                    break;

                                case 2:
                                    insur_type = "LIFE";
                                    baseRate = 800;
                                    break;

                                case 3:
                                    insur_type = "HEALTH";
                                    baseRate = 900;
                                    break;
                                
                                case 4:
                                    insur_type = "VEHICLE";
                                    baseRate = 900;
                                    break;
                                default:
                                    System.out.println("ERROR: Sadly, we do not offer the policy you entered, please try another options.");
                                    return true;
                            }

                            System.out.println("Congradulations on selecting "+insur_type+" insurance for one article!");
                            System.out.println("Next, County Garden needs a rough description of what exactly you want covered.");
                            System.out.println("Keep in mind, County Garden will be less likely to cover items that do not match");
                            System.out.println("the insurance type or are unrealistic for a smaller size insurance company to cover.");
                            System.out.println("For the coverage details, please check with upper management's documentation (README).\n");

                            System.out.println("Please provide a brief description the article\n");
                            article = userInput(in, "Enter Article Description");


                            System.out.print("Lastly, we would like for you to provide a rough estimation of on the value");
                            System.out.print("of the article. This value may be adjusted by employees, but we would like");
                            System.out.print("to see how much you value your articles.\n");
                            
                            esti_value = ""+getFloat(in, "Article Value");

                            break;
                        }

                        if((new_policy = db.insertCustomerPolicy(idx, insur_type, baseRate, article, esti_value)) == null) {
                            System.out.println("ERROR: Could not create your policy, please try again following the instructions.\n");
                            return true;
                        }

                        System.out.println("Congradulations for your new policy!\n");

                        printRows(new_policy);

                        return true;
                
                    case '-':

                        printRows(db.getAllCustomerPolicies(idx));

                        p_id = -1;

                        p_id = getInt(in, "Enter policy ID");

                        if(db.deleteCustomerPolicy(idx, p_id) == -1) {
                            System.out.println("ERROR: Could not remove policy\n");
                        }
                        else {
                            System.out.println("We have succesfully closed you policy.\n");
                        }
                        
                        return true;
        
                    case 'M':
                        /* print valid policy id, item id, article desc */

                        String choice = null;
                        p_id = -1;

                        System.out.println("Would you like to remove or add items to your policy?");
                        
                        while(true) {
                            if((choice = userInput(in, "Enter \"remove\" or \"add\""))== null) return true;
                            choice = choice.toUpperCase();
                            if((choice=="REMOVE") || (choice=="ADD")){
                                break;
                            }
                            System.out.println("ERROR: Please type a valid option!");
                        }

                        printRows(db.getCustomerItems(idx));

                        p_id = getInt(in, "Enter policy id");

                        if(choice == "REMOVE") {
                            i_id = getInt(in, "Enter item id");
                            if(db.deleteCustomerItem(p_id, i_id) == -1) {
                                System.out.println("ERROR: The policy or item id may be invalid. Returning...");
                                return true;
                            };
                            
                            System.out.println("Item removed. Returning...");
                        }
                        else if(choice == "ADD") {
                            i_id = getInt(in, "Enter item id");


                            if(db.insertCustomerItem(p_id, i_id, userInput(in, "Enter article description"), getFloat(in, "Enter article value")) == null) {
                                System.out.println("ERROR: The policy or item id may be invalid. Returning...");
                                return true;
                            };
                            
                            System.out.println("Item added. Returning...");
                        }

                        
                        return true;
        
                    case '?':
                        menu(InterfaceSetting.customer);
                        return true;

                    case 'q':
                        System.out.println("Returning to main interface...");
                        return true;
                
                    default:
                        break;
                
                    
                }

                return true;
            
            case 'C':

                /* TODO: print all claims made by the user */
                System.out.println("\nClaim options");
                System.out.println("\t[V] View claim");
                System.out.println("\t[+] Add claim");
                System.out.println("\t[-] Nullify claim");
                System.out.println("\t[?] Display overview menu");
                System.out.println("\t[q] quit interface\n");

                System.out.println("Please select one of the options above.");

                action = prompt(in, "V+-?q");

                switch (action) {
                        
                    case 'V':
                        printRows(db.getAllCustomerClaims(idx));

                        cl_id = getInt(in, "Enter client id");
                            
                        List<List<String>> claim = db.getCustomerClaim(idx, cl_id);

                        if(claim != null) printRows(claim);
                        else {
                            System.out.println("ERROR: Could not retrieve claim, may be incorrect claim id.");
                            return true;
                        }

                        System.out.println("The description of the claim is listed above.ln");
                        
                        return true;
                
                    case '+':
                        
                        /* print customer items under valid policies */
                        /* pick a customer item, describe event*/
                        /* send customer id, event describtion and item damaged */

                        printRows(db.getCustomerItems(idx));

                        System.out.println("Please select the item that was affected.");

                        i_id = getInt(in, "Enter item id");

                        System.out.println("While we are sad to hear the news, County Garden is happy");
                        System.out.println("to offer its services in remediation. First, we will need");
                        System.out.println("a brief description of what happened in this event.");
                        System.out.println("This description will be used to verify the claim, and");
                        System.out.println("Adjust the remediation/reimbursement accordingly.\n");

                        System.out.println("Now, please type the event description.\n");

                        event = userInput(in, "Enter event description");

                        if(db.insertCustomerClaim(idx, i_id, event) == null) {
                            System.out.println("ERROR: Your item id may not be valid.");
                        }

                        System.out.println("Your claim has been submitted for review!");
                        System.out.println("You may check its status by viewing your claims.");


                        return true;
                
                    case '-':
                        
                        /* print valid pending claims */
                        /* select valid claims */
                        /* update valid claim to rejected */

                        printRows(db.getCustomerPendingClaims(idx));

                        cl_id = getInt(in, "Enter a claim you would like to nullify");

                        if(db.updateCustomerClaim(idx, cl_id, "REJECTED") == null) {
                            System.out.println("ERROR: The claim could not be updated. Please try the claim id again");
                        }
                        System.out.println("The claim was has been updated to REJECTED.");
                        System.out.println("You may check its status by viewing your claims.");

                        return true;
        
                    case '?':
                        menu(InterfaceSetting.customer);
                        return true;

                    case 'q':
                        System.out.println("Returning to main interface...");
                        return true;
                
                    default:
                        break;
                }
                
                return true;
        
            case 'T':

                /* TODO: print all claims made by the user */
                System.out.println("Transaction Options");
                System.out.println("\t[V] View active inquries");
                System.out.println("\t[+] Make payment");
                System.out.println("\t[*] View all payments");
                System.out.println("\t[?] Display overview menu");
                System.out.println("\t[q] quit interface\n");

                action = prompt(in, "V+*?q");

                switch (action) {
                        
                    case 'V':
                
                        /* print inquries for valid policies (end date = null) */
                        /* select tranasaction to view payments */
                        /* print payments for the transaction id given customer id */

                        printRows(db.getAllCustomerInquires(idx));

                        System.out.println("Please enter a transaction id from the above inquires");
                        System.out.println("to view the current payments made towards that active policy.\n");

                        t_id = getInt(in, "Enter Transaction ID");

                        printRows(db.getCustomerPaymentsTransactions(idx, t_id));

                        return true;
                
                    case '+':
                    
                        /* print inquries for valid policies */
                        /* select inqury to pay for and amount to pay */
                        /* make new payment with t_id, idx, and payment amount */

                        printRows(db.getAllCustomerInquires(idx));

                        System.out.println("Please enter a transaction id from the above inquires");
                        System.out.println("to select for making a payment.\n");

                        t_id = getInt(in, "Enter Transaction ID");
                        
                        value = getFloat(in, "Enter Float");

                        if(db.insertCustomerPayment(idx, t_id, value) == null) {
                            System.out.println("ERROR: Payment was invalid! Check if transaction id is valid and payment is postive.");
                            return true;
                        }

                        System.out.println("Payment was accepted and charged based on your payment method!");

                        return true;
                
                    case '*':
                        
                        /* print all payments made */

                        printRows(db.getAllCustomerPayments(idx));

                        System.out.println("Above are all the payments associated with County Garden.\n");

                        return true;
        
                    case '?':
                        menu(InterfaceSetting.customer);
                        return true;

                    case 'q':
                        System.out.println("Returning to main interface...");
                        return true;
                
                    default:
                        break;
                }
                
                return true;

            case 'q':
                System.out.println("Quit option selected:\n\nThank you for using our services!\n");
                return false;

            case '?':
                menu(InterfaceSetting.customer);
                return true;

            default:
                break;
        }

        return false;
    }

    static void useInterface(Database db, BufferedReader in, InterfaceSetting ifs) {

        switch(ifs) {
            case corporate:
                /*TODO*/
                return;

            case customer:

                int cus_id = -1;

                System.out.println("\n\n\n-------------------Customer Interface:--------------------\n");
                System.out.println("Welcome to the customer interface!");

                while (cus_id == -1) {
                    cus_id = customerSignIn(db, in);
                }

                while (true) {

                    if(!custAction(db, in, cus_id)) break;
                    
                }
                return;

            case agent:
                
                int agent_id = -1;

                System.out.println("\n\n\n-------------------Agent Interface:--------------------\n");
                System.out.println("Welcome to the agent interface!");

                while (agent_id == -1) {
                    agent_id = agentSignIn(db, in);
                }

                while (true) {

                    if(!agentAction(db, in, agent_id)) break;
                    
                }

                return;

            case adjuster:
                int adj_id = -1;

                System.out.println("\n\n\n-------------------Agent Interface:--------------------\n");
                System.out.println("Welcome to the agent interface!");

                while (adj_id == -1) {
                    adj_id = agentSignIn(db, in);
                }

                while (true) {

                    if(!adjusterAction(db, in, adj_id)) break;
                    
                }
                return;
                
            default:
                /* error */
                return;

        }
    }

    /**
     * The main routine runs a loop that gets a request from the user and
     * processes it
     * 
     * @param argv Command-line options.  Ignored by this program.
     */
    public static void main(String[] argv) {

        /*
        TODO:
        
        - Allow user to quit if they do not wish to sign in
        - 
        
        */
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        
        Database db = null;
        String dbRef = "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241";
        String user = null;
        String pass = null;
        
        System.out.println("\n\n\n-----------------Welcome to County Garden-----------------\n");

        
        while(db == null) {
            

            System.out.println("Please enter your valid credentials\n");
            System.out.println("E.g:\n\t\"Database Username:> xyz123\"");
            System.out.println("\t\"Database Password:> ProfKorthisCool\"\n");

            if((user = userInput(in, "Database Username")) == null) continue;
            if((pass = userInput(in, "Database Password")) == null) continue;

            db = Database.getDatabase(dbRef,user,pass);
        }
        

        // Get a fully-configured connection to the database, or exit 
        // immediately
        
        /*Database db = Database.getDatabase(user, pass);
        if (db == null)
            return;*/

        // Start our basic command-line interpreter:

        /* interface selection code can be condensed */
        
        while(true) {
            InterfaceSetting ifs = pickInterface(in);
            useInterface(db, in, ifs);


            break; /* left for debugging purposes */
        }
        
        // Always remember to disconnect from the database when the program 
        // exits
        if(db != null) db.disconnect();
    }
}