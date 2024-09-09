Prrogramming a well-known ordinary differential
equation (ODE) known as Romeo and Juliet over a TCP/IP client-server
architecture.
The figurative scenario is a variant of the famous Romeo and Juliet play
by William Shakespeare. Again, you do not have to have read the play
in order to understand or solve the assignment. In the original tragedy,
the two lovers face an impossible relation because of the ongoing rivalry
between their families. Here, our scenario is just slightly different; whereby
the playwriter, William Shakespeare, gets acquaintance with the two lovers
and an exchange of love letters commence with the playwriter playing a kind
of matchmaker between the two while writing the play about the love story.
In this figurative scenario, the verses of the play correspond to the different
values of the ODE over time.
The servers (Romeo and Juliet) are analgous in the service they provide although
each one solve one of the equations of the Romeo and Juliet ODE. They are
single-threaded. The client, i.e. the Playwriter, will be responsible to com-
municate with the servers to get the ODE values over time (i.e. iterations).
For each verse of the play (i.e. iteration of the ODE), the Playwriter will
request the service from both servers and annotate their answers in the novel.
At the end of the iterations, the novel will be dumped into a .csv file. The
method to do so is also provided to you, so you do not have to worry about
it. 

**Methods description**

Juliet.java
Juliet is a server. So its duty is to accept service requests,
process those requests and then send back the request outcome to the client.
The service is calculating the next value of the ODE sinusoidal part.

• public Juliet(double initialLove): Class constructor. Create the
server socket. The IP address corresponds to the localhost (i.e. 127.0.0.1).
The port can be any number, but you are suggested to use 7779 for Juliet.
The initialLove value helps to intialize the attribute currentLove.

• public Pair¡InetAddress,Integer¿ getAcquaintance(): Receives lover’s
socket information and share’s own socket. This method is called by the
playwriter. In this method, the server (Juliet) returns the server IP ad-
dress and its port number.

• public double receiveLoveLetter(): Retrieves the lover’s love. This
method accepts the server’s service requests. Upon receiving the service
request, it parses the message to remove the termination character from
the incoming message. The remaining part of the incoming message
contains the current ODE value from the counterpart server (Romeo) via
the client (the playwriter). Note that the message will arrive in the form
of a string so a casting to a double will be required before processing the
request.

• public double renovateLove(double partnerLove): Provided. No
need to do anything. The ODE system. Given the lover’s love at time
t, estimate the next love value for Romeo at time t + 1. This method
provides the actual service. Note how the value of the ODE is stored in
attribute currentLove.

• public void declareLove(): Communicate love back to playwriter.
This method prints Juliet’s good night’s message (Good night, good night!
Parting is such sweet sorrow,\n That I shall say good night till it be mor-
row.) and return the outcome of the request to the client; that is, it
sends back to the playwriter a message with the ODE value (Juliet’s
currentLove). The message uses a termination character. Although the
termination character can be any character that it is not part of the
message, we suggest you use “J” for traceability.

Romeo.java
Romeo is a server. So its duty is to accept service
requests, process those requests and then send back the request outcome to
the client. The service is calculating the next value of the ODE cosinusoidal part.

• public Romeo(double initialLove): Class constructor. Create the
server socket. The IP address corresponds to the localhost (i.e. 127.0.0.1).
The port can be any number, but you are suggested to use 7778 for
Romero. The initialLove value helps to intialize the attribute currentLove.

• public Pair¡InetAddress,Integer¿ getAcquaintance(): Receives lover’s
socket information and share’s own socket. This method is called by the
playwriter. In this method, the server (Romeo) returns the server IP
address and its port number.

• public double receiveLoveLetter(): Retrieves the lover’s love. This
method accepts the server’s service requests. Upon receiving the service
request, it parses the message to remove the termination character from
the incoming message. The remaining part of the incoming message
contains the current ODE value from the counterpart server (Romeo) via
the client (the playwriter). Note that the message will arrive in the form
of a string so a casting to a double will be required before processing the
request.

• public double renovateLove(double partnerLove): Provided. No
need to do anything. The ODE system. Given the lover’s love at time
t, estimate the next love value for Romeo at time t + 1. This method
provides the actual service. Note how the value of the ODE is stored in
attribute currentLove.

• public void declareLove(): Communicate love back to playwriter.
This method prints Romeo’s good night’s message (I would I were thy
bird ) and return the outcome of the request to the client; that is, it
sends back to the playwriter a message with the ODE value (Romeo’s
currentLove). The message uses a termination character. Although the
termination character can be any character that it is not part of the
message, we suggest you use “R” for traceability.

PlayWriter.java
PlayWriter is a client to both servers. So its duty is to
request both services, and wait to be responded with the request outcome.
Both services are requested a number of times (the novelLength) before the
final output (the novel) is dumped to a .csv file

• public PlayWriter(): Provided. No need to do anything. Class con-
structor. Initializes all the attributes of the class.

• public void createCharacters(): Create the lovers. Create one object
of type Romeo and one object of type Juliet and saves them to attributes
myRomeo and myJuliet respectively. Then, it launches the execution of
the corresponding threads.

• public void charactersMakeAcquaintances(): This method interro-
gate each server for their IP address and port number.

• public void requestVerseFromRomeo(int verse): Connects to the
server Romeo and request service from the Romeo server. In order to do
so, it has to create a service request message containing the counterpart
server service value i.e. Juliet’s love, and add a terminating character.
Although the termination character can be any character that it is not
part of the message, we suggest you use “J” for traceability. Once the
message is ready, it does send the message to the Romeo server to indicate
the request of the service. The method’s parameter verse helps to keep
track of the iteration and hence where from the novel array should Juliet’s
current value be read.

• public void requestVerseFromJuliet(int verse): Analogous to the
service request method for the Romeo server but for server Juliet. Al-
though the termination character can be any character that it is not part
of the message, we suggest you use “R” for traceability.

• public void receiveLetterFromRomeo(int verse): Received the out-
come of the service from the server Romeo. Upon receiving this outcome,
it parses the message to remove the termination character from the in-
coming message. The remaining part of the incoming message contains
the new ODE value from the server (Romeo). Note that the message
will arrive in the form of a string and with a termination character, so a
removal of the termination character and a casting to a double will be re-
quired before saving the ODE’s value into the novel array. The method’s
parameter verse helps to keep track of the iteration and hence where to
in the novel array should Romeo’s new value be written. Finally, it closes
the connection with the server’s socket.

• public void receiveLetterFromJuliet(int verse): Analogous to the
service outcome reception method for the Romeo server but for server
Juliet.

• public void storyClimax(): This is the main loop where the services
from both servers are requested and received.

• public void charactersDeath(): Terminates the execution of both
servers.
