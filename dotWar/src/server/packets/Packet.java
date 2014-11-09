package server.packets;

/**
 * Created by Zane on 2014-11-08.
 */
public abstract class Packet {
    public static enum PacketTypes{
        INVALID(-1), LOGIN(00),CHAT(2);
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
        for(PacketTypes pt:PacketTypes.values()){
            if(pt.getPacketID() == packetID){
                return pt;
            }
        }
        return PacketTypes.INVALID;
    }
    public String getName(){
        for(PacketTypes pt:PacketTypes.values()){
            if(pt.getPacketID() == packetID){
                return pt.getName();
            }
        }
        return PacketTypes.INVALID.name();
    }
}
