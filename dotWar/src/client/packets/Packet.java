/*
 * Copyright (c) 2014 Zane Ouimet, Nicholas Wilkinson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package client.packets;

/**
 * Created by Zane on 2014-11-08.
 */
public abstract class Packet {
    public static enum PacketTypes{
        INVALID(-1), CONNECT(00),CHAT(2);
        private int packetID;
        PacketTypes(int packetID){
            this.packetID = packetID;
        }
        public int getPacketID(){
            return packetID;
        }
        public String getName(){
            return name();
        }

    }
    public int packetID;

    public Packet(int packetID){
        this.packetID = packetID;
    }

    public static PacketTypes lookupPacketType(int packetID){
        for(PacketTypes pt: PacketTypes.values()){
            if(pt.getPacketID() == packetID){
                return pt;
            }
        }
        return PacketTypes.INVALID;
    }
    public String getName(){
        for(PacketTypes pt: PacketTypes.values()){
            if(pt.getPacketID() == packetID){
                return pt.getName();
            }
        }
        return PacketTypes.INVALID.name();
    }
}
