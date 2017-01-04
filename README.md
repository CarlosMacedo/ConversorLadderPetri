# Conversor Ladder to Petri (LtP)

LtP is a converter that transforms Ladder Diagrams (LD) into Colored Petri Net (RPC).
However, LtP is restricted to the basic elements of the LD, that is, it only accepts LD files that
Have only combinations of contacts and coils.

There are several tools for editing LD and RPC, among them LDmicro and CPN Tools. The converter
Was built for these two tools. LtP converts the LD created in the LDmicro into an RPC
Compatible with CPN Tools 4.0.1.

The only requirement that LtP has to function properly is that all contacts (inputs)
Begin with the letter "I" and the outputs start with the letter "O".

LDmicro: http://cq.cx/ladder.pl

CPNTools: http://cpntools.org/

Author of the thesis work and Developer: Carlos Henrique de Macêdo (http://lattes.cnpq.br/1382662723668271)
