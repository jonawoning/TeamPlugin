package nl.snowpix.kingdom.tab.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class PacketsData {

    @Getter
    @AllArgsConstructor
    enum PacketData {

        v1_16("h", "c", "d", "a", "i", "j", "b", "g", "f", "e");

        private String members;
        private String prefix;
        private String suffix;
        private String teamName;
        private String paramInt;
        private String packOption;
        private String displayName;
        private String color;
        private String push;
        private String visibility;

    }

}
