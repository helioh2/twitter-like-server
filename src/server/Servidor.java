package server;

import exceptions.FollowedDoesNotExistException;
import exceptions.FollowerDoesNotExistException;
import exceptions.HashTagDoesNotExistsException;
import exceptions.InvalidUsernameException;
import exceptions.MaxStringWeightException;
import exceptions.SameUserException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserAlreadyFollowedException;
import exceptions.UserDoesNotExistException;
import exceptions.UserIsNotFollowedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.print.attribute.standard.OutputDeviceAssigned;

public class Servidor {
    
    private static final int PORTA = 1234;
    private SocialNetworkDatabase database;
    
    public void iniciar() throws IOException {
        ServerSocket socket = new ServerSocket(PORTA);
        database = new SocialNetworkDatabase();

        try {
            while (true) {
                atenderCliente(socket.accept());
            }
        } finally {
            socket.close();
        }
    }
    
    private void atenderCliente(final Socket cliente) {        
        // A ideia basica para atender um cliente é
        //   - ler comando
        //   - processar comando
        //   - escrever resposta
        // Você deve fazer o controle da concorrência, pois vários
        // clientes podem ser atendindos concorrentemente
        // Você pode criar uma thread para atender cada cliente
        
        new Thread() {

            @Override
            public void run() {
                
                ArrayList<String> out_list; //Lista de retorno
        
                String command = null;
                
                try {
                    command = readLine(cliente.getInputStream());
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }

                out_list = handleCommand(command); 
                
                try {
                    for (String line: out_list){
                        writeLine(cliente.getOutputStream(), line);
                    }
                    
                    cliente.close();
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        }.start(); 
    }
    
    private static String readLine(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        return reader.readLine();
    }
    
    private static void writeLine(OutputStream out, String linhas) throws IOException {
        out.write(linhas.getBytes());
        out.write('\n');
    }


    private ArrayList<String> handleCommand(String command){
        
        ArrayList<String> out_list = new ArrayList<String>();
        String command_type = command.split(" ")[0];
        
        if (command_type.equals(Command.CREATE_USER.toString())){
            out_list = createUser(command);
        } else if (command_type.equals(Command.POST_MESSAGE.toString())){
            out_list = postMessage(command);
        } else if (command_type.equals(Command.FOLLOW.toString())
                || command_type.equals(Command.UNFOLLOW.toString())){
            //comando follow e unfollow
            out_list = followOrUnfollow(command);
        } else if (command_type.equals(Command.LIST_USER_MESSAGES.toString())
                || command_type.equals(Command.LIST_FOLLOWERS.toString())
                || command_type.equals(Command.LIST_FOLLOWING.toString())
                || command_type.equals(Command.LIST_FOLLOWING_MESSAGES.toString())
                || command_type.equals(Command.LIST_STATISTICS.toString())){
            //comando de listar com um argumento
            out_list = listSomethingAboutUser(command);
        } else if (command_type.equals(Command.LIST_TREND_TOPICS.toString())){
            out_list = listTrendTopics(command);
        } else if (command_type.equals(Command.LIST_MESSAGES_WITH_HASHTAG.toString())) {
            out_list = listMessagesWithHashtag(command);
        } else if (command_type.equals(Command.RESET.toString())){
            database = new SocialNetworkDatabase();
        }
        else out_list.add(OutputMessage.INVALID_COMMAND.toString());
        
        return out_list;

    }

    private ArrayList<String> createUser(String command) {
        ArrayList<String> ret = new ArrayList<String>();
        String[] substrings = command.split(" ");
        
        if (substrings.length != 2){
            ret.add(OutputMessage.INVALID_COMMAND.toString());
            return ret;
        } else {
            String username = substrings[1];
            try {
                database.addUser(username);
                ret.add(OutputMessage.SUCCESS.toString());
            } catch (UserAlreadyExistsException ex) {
                ret.add(OutputMessage.USER_ALREADY_EXISTS.toString());
            } catch (InvalidUsernameException ex) {
                ret.add(OutputMessage.INVALID_NAME.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                return ret;
            }
        }
    }

    private ArrayList<String> postMessage(String command) {
        
        ArrayList<String> ret = new ArrayList<String>();
        String[] substrings = command.split(" ");
        
        if (substrings.length == 2){
            ret.add(OutputMessage.INVALID_MESSAGE.toString());
            return ret;
        } 
        if (substrings.length < 2){
            ret.add(OutputMessage.INVALID_COMMAND.toString());
            return ret;
        } else {
            String username = substrings[1];
            String message = "";
            for (int i=2; i<substrings.length; i++){
                message+=substrings[i]+" ";
            }
            message = message.substring(0, message.length()-1);
            
            try {
                database.postMessage(username, message);
                ret.add(OutputMessage.SUCCESS.toString());
            } catch (UserDoesNotExistException ex) {
                ret.add(OutputMessage.USER_DOESNT_EXIST.toString());
            } catch (MaxStringWeightException ex) {
                ret.add(OutputMessage.INVALID_MESSAGE.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                return ret;
            }
        }
        
    }

    private ArrayList<String> listSomethingAboutUser(String command) {
        
        ArrayList<String> ret = new ArrayList<String>();
        String[] substrings = command.split(" ");
        
        if (substrings.length != 2){
            ret.add(OutputMessage.INVALID_COMMAND.toString());
            return ret;
        } else {
            String username = substrings[1];
            try {
                if (substrings[0].equals(Command.LIST_FOLLOWERS.toString())){
                    ret = database.listFollowers(username);
                } else if (substrings[0].equals(Command.LIST_FOLLOWING.toString())){
                    ret = database.listFollowing(username);
                } else if (substrings[0].equals(Command.LIST_FOLLOWING_MESSAGES.toString())){
                    ret = database.listFollowingMessages(username);
                } else if (substrings[0].equals(Command.LIST_STATISTICS.toString())){
                    ret = database.listStatistics(username);
                } else if (substrings[0].equals(Command.LIST_USER_MESSAGES.toString())){
                    ret = database.listMessages(username);
                }
            } catch (UserDoesNotExistException ex) {
                ret.add(OutputMessage.USER_DOESNT_EXIST.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                return ret;
            }
        }
    }

    private ArrayList<String> followOrUnfollow(String command) {
        ArrayList<String> ret = new ArrayList<String>();
        String[] substrings = command.split(" ");
        
        if (substrings.length != 3){
            ret.add(OutputMessage.INVALID_COMMAND.toString());
            return ret;
        } else {
            String username1 = substrings[1];
            String username2 = substrings[2];
            try {
                if (substrings[0].equals(Command.FOLLOW.toString())){
                    database.follow(username1, username2);
                } else {
                    database.unfollow(username1, username2);
                }
                ret.add(OutputMessage.SUCCESS.toString());
            } catch (FollowerDoesNotExistException ex) {
                ret.add(OutputMessage.FOLLOWER_DOESNT_EXIST.toString());
            } catch (FollowedDoesNotExistException ex) {
                ret.add(OutputMessage.FOLLOWED_DOESNT_EXIST.toString());
            } catch (UserIsNotFollowedException ex) {
                ret.add(OutputMessage.USER_IS_NOT_FOLLOWED.toString());
            } catch (UserAlreadyFollowedException ex) {
                ret.add(OutputMessage.USER_IS_ALREADY_FOLLOWED.toString());
            } catch (SameUserException ex) {
                ret.add(OutputMessage.SAME_USER.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                return ret;
            }
        }
    }

    

    private ArrayList<String> listTrendTopics(String command) {
        ArrayList<String> ret = new ArrayList<String>();
        String[] substrings = command.split(" ");
        
        if (substrings.length != 1){
            ret.add(OutputMessage.INVALID_COMMAND.toString());
            return ret;
        } else {
            ret = database.listTrendTopics();
            return ret;
        }
    }

    private ArrayList<String> listMessagesWithHashtag(String command) {
        ArrayList<String> ret = new ArrayList<String>();
        String[] substrings = command.split(" ");
        
        if (substrings.length != 2){
            ret.add(OutputMessage.INVALID_COMMAND.toString());
            return ret;
        } else {
            String hashtag = substrings[1];
            try {
                ret = database.listMessagesWithHashtag(hashtag);
            } catch (HashTagDoesNotExistsException ex) {
                ret.add(OutputMessage.HASHTAG_DOESNT_EXIST.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                return ret;
            }
        }
    }
    
}