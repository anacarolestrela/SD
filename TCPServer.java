import java.io.* ;
import java.net.* ;

public class TCPServer
{
    private static int clientCount = 0;
    public static void main(String args[]){
        
        try{
            
// Criação do socket do Servidor, utilizando a porta serverPort.
            int serverPort=8000;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("Sever inicializado");

// Loop principal de espera por novos Clientes.            
            while(true)
            {

// Servidor aguarda a conexão de algum Cliente.
                Socket clientSocket = listenSocket.accept();
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                int clientId = ++clientCount;
                // Envia a mensagem de boas-vindas para o cliente
                out.writeUTF("Bem-vindo ao servidor!Seu ID de cliente é: " + clientId);

// Criação da Thread e conexão desta ao socket do Cliente.
                ConnectionTCP c = new ConnectionTCP(clientSocket, clientId);
                System.out.println("Conectou! o cliente"+clientId);
            }
        }
        catch(IOException e) {System.out.println("Listen:" + e.getMessage());}
    }
}

class ConnectionTCP extends Thread 
{
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    int clientId;

// Conexão da Thread-Servidora com o socket do Cliente e
// criação dos fluxos de I/O. (início)    
    public ConnectionTCP (Socket aClientSocket, int clientId){
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream( clientSocket.getInputStream());
            out = new DataOutputStream( clientSocket.getOutputStream());
            this.clientId = clientId;
            this.start();
        } 
        catch(IOException e)
        {
            System.out.println("Connection:" + e.getMessage());
        }
    }
// Conexão da Thread-Servidora com o socket do Cliente e
// criação dos fluxos de I/O. (final)     
    
// Conversa entre a Thread-Servidor com o Cliente. (início)
    public void run() 
    {
        try {
            // Recebe as três notas do cliente
            double nota1 = in.readDouble();
            double nota2 = in.readDouble();
            double nota3 = in.readDouble();
            
            // Calcula a média das notas
            double media = (nota1 + nota2 + nota3) / 3.0;
            
            // Envia a média de volta para o cliente
            out.writeDouble(media);
            
            System.out.println("Média enviada para o cliente "+clientId+ ":" + media);
        } 
        catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } 
        finally 
        {
            try 
            {
                clientSocket.close();
            } 
            catch (IOException e) 
            {
                System.out.println("IO:" + e.getMessage());
            }
        }
    }
}