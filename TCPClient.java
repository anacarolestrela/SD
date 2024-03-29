/**
 * 
 * Classe que cria um Cliente TCP que se comunica através de Sockets com um Servidor
 * 
 */

 import java.io.* ;
 import java.net.* ;
 import java.util.Scanner;

 
 public class TCPClient
 {
    public static void main (String args[]){
         
 // Criação do socket do Cliente, acessando o endereço/porta do Servidor  e
 // criação dos fluxos de I/O. (início)
        Socket s = null;
        try{
            int serverPort=8000;
            s = new Socket("127.0.0.1", serverPort);
            DataInputStream in = new DataInputStream( s.getInputStream());
            String welcomeMessage = in.readUTF();
            System.out.println(welcomeMessage);
            DataOutputStream out = new DataOutputStream( s.getOutputStream());
            try (Scanner scanner = new Scanner(System.in)) 
            {
                // Conversa entre o Cliente e o Servidor. (início)
                 //Solicitação de notas do client
                System.out.print("Digite a primeira nota: ");
                double nota1 = scanner.nextDouble();
                System.out.print("Digite a segunda nota: ");
                double nota2 = scanner.nextDouble();
                System.out.print("Digite a terceira nota: ");
                double nota3 = scanner.nextDouble();
                
                // Envia as três notas para o servidor
                out.writeDouble(nota1);
                out.writeDouble(nota2);
                out.writeDouble(nota3);
            }

 // Criação do socket do Cliente, acessando o endereço/porta do Servidor  e
 // criação dos fluxos de I/O. (final) 
             
 // Recebe a média do servidor
        double media = in.readDouble();
        System.out.println("Média recebida do servidor: " + media);
 // Conversa entre o Cliente e o Servidor. (final)            
 
 // Tratamento de Exceções. (início)
        }
        catch (UnknownHostException e)
        {
            System.out.println("Sock:" + e.getMessage());
        }
        catch (EOFException e)
        {
            System.out.println("EOF:" + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println("IO:" + e.getMessage());
        }
        finally 
        {
            if(s!=null) 
                try 
                {
                    s.close();
                } 
            catch (IOException e)
            {
                System.out.println("IO:"+e.getMessage());
            }
        }
 // Tratamento de Exceções. (final)
     }
 }