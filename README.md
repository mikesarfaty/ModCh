# ModCh
#### MODCH HAS NO SECURITY, NO ENCRYPTION, DOES NOT USE SSL ####
#### I run this on my local machine and with my friends who aren't trying to steal my data ####

Overview of important classes:

# Accept Users #
This runs in a separate thread, and is responsible for taking on new users. Runs in a separate thread so that if a user is trying to connect before another user has finished connecting (ie typed name) the second user won't get a timeout
    
# ChatModule (Trait) and subclasses #
This is what the whole thing is about, baby. But actually, the ChatModule is what lays the framework for how addons are dealt with. As I add more addons I will no doubt restructure what exactly a chatmodule is, but for now I'm happy with the things I allow "third-party" classes access to
    
# ReadIn #
Remember in Lord of the Rings, the "One Ring to rule them all"? That's what this class is. It's the one class to rule all inputs. It grabs information from every client, parses it, and adds it to the queue. 
NOTE: for now, this is where ChatModule parsing happens. This will probably change in the future. For now, I think it makes more logical sense to read and parse an input, then send it to the output.
    
# ReadOut #
    Pretty simple, this thread is responsible for sending outputs to every client. Note the three different ways to send stuff:
        1. Send all queued messages to all users
        2. Send a specific message to all users
        3. Send a specific message to a specific user
    Messages are filtered so that you don't get a message back that you sent.
        
# Client #
Right now, clients are unique by name. I plan, very soon, to have account support for clients to have saved stats and data. I would do this by way of addon (like I said, base model simple!) where the command is !info <user> and I can have people register with !register-info <user> <info-type> <info>
Clients are basically a function of input/output streams, with names to keep track of what goes where.

If you want to know details about a specific module, please check the file/documentation.

PLANNED (as of 3/26) [in order of when I'm doing it]
-- CLIENT (that way I can get away from telnet)
      add-i: right now, client is planned in Scala but might try Python instead.
-- Profile addon (described above)
-- CLIENT-ADDONS
    reason: allow the same modularity as with servers

PLANNED ADDONS (besides profile, because that's a little more pertinent)
-- TIC-TAC-TOE
    reason: proof of concept to work with text based images and games between two users
-- DEPENDENCY
    reason: let modules work together. For example, TIC-TAC-TOE could require a DirectMessage dependency to avoid a public game of tic tac toe
-- ServerLog
    reason: Don't have to use the command line to monitor the server anymore
-- Persistence
    reason: auto-restart the server if it goes down. This one's tricky, it would need to launch into a separate process that itself cannot crash. I need to do more research on this
-- Voice (in-sync with client addon)
    info: this is very ambitious. But would be so cool.
-- Login/verifyMe!
    info: Hosted on separate central server, allow users to be verified (across multiple servers)
-- Download
    reason: Download the conversation this session
