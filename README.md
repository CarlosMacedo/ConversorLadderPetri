# Conversor Ladder to Petri (LtP)

## Resume

There are several critical processes in the industry that are automated with the goal of optimizing
the available resources and increasing the production such as gas and oil. Ladder is a language
used in the development of systems developed to the industry that use Programmable Logic
Controllers. However, the systems have reliability and security issues. To simulate and verify the
properties of the systems, it is used mathematical tools like Petri Nets (PN). Some researchers
propose methods to convert Ladder in some PN class. Despite the large number of existing
conversion methods, there are no implementations of these methods that can be used in the
industry without the implementation of additional features in the existing tools. This work
presents the existing conversion methods, compares them briefly, and presents an implementation
of one of them. This work presents the LtP converter. LtP converts Ladder to Colored Petri
Nets, which is a PN class. In addition, the LtP is compared with the only converter found in the
literature.


Full explanation here: https://github.com/CarlosMacedo/ConversorLadderPetri/blob/master/TCC___13111986.pdf



## LtP

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



## Example

Before

<img src="https://github.com/CarlosMacedo/ConversorLadderPetri/blob/master/LadderDiagram.png" alt="LadderDiagram" width="300">

After

<img src="https://github.com/CarlosMacedo/ConversorLadderPetri/blob/master/PetriNet.png" alt="PetriNet" width="300">

