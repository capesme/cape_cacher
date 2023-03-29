package com.github.voxxin.cape_cacher.task;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.config.ModConfig;
import com.github.voxxin.cape_cacher.config.ModMenu;

public class IdentifyCapeType {
    public static String[] CapeIdentifier(String CapeURL) {
        String[] capeInfo = {"", "", ""};

        if (!CapeURL.contains("http://textures.minecraft.net/texture/")) return capeInfo;
        CapeURL = CapeURL.replace("http://textures.minecraft.net/texture/", "");

        // / Formatting

        /*
        capeInfo[0] is the cape's name.
        capeInfo[1] is the cape's code!
F
        capeInfo[2] is the cape's 'rarity'.

         */

        // / Notes

        /*
        There are some bedrock capes at the bottom.
        This doesn't mean you'll ever incounter them in game.
        I only added them because I noticed they were in the texture DB.
         */

        if(!ModConfig.getNotifyMessageAll()) return capeInfo;


        switch (CapeURL) {
            case "f9a76537647989f9a0b6d001e320dac591c359e9e61a31f4ce11c88f207f0ad4" -> {
                if(!CapeCacher.config.capes.vanillaOptions) return capeInfo;

                capeInfo[0] = "Vanilla Cape";
                capeInfo[1] = "vanilla";
                capeInfo[2] = "0";
                return capeInfo;
            }
            case "2340c0e03dd24a11b15a8b33c2a7e9e32abb2051b2481d0ba7defd635ca7a933" -> {
                if(!CapeCacher.config.capes.migratorOptions) return capeInfo;

                capeInfo[0] = "Migrator";
                capeInfo[1] = "mig";
                capeInfo[2] = "0";
                return capeInfo;
            }
            case "953cac8b779fe41383e675ee2b86071a71658f2180f56fbce8aa315ea70e2ed6" -> {
                if(!CapeCacher.config.capes.mineCon2011Options) return capeInfo;

                capeInfo[0] = "MineCon 2011";
                capeInfo[1] = "m11";
                capeInfo[2] = "1";
                return capeInfo;
            }
            case "a2e8d97ec79100e90a75d369d1b3ba81273c4f82bc1b737e934eed4a854be1b6" -> {
                if(!CapeCacher.config.capes.mineCon2012Options) return capeInfo;

                capeInfo[0] = "MineCon 2012";
                capeInfo[1] = "m12";
                capeInfo[2] = "1";
                return capeInfo;
            }
            case "153b1a0dfcbae953cdeb6f2c2bf6bf79943239b1372780da44bcbb29273131da" -> {
                if(!CapeCacher.config.capes.mineCon2013Options) return capeInfo;

                capeInfo[0] = "MineCon 2013";
                capeInfo[1] = "m13";
                capeInfo[2] = "1";
                return capeInfo;
            }
            case "b0cc08840700447322d953a02b965f1d65a13a603bf64b17c803c21446fe1635" -> {
                if(!CapeCacher.config.capes.mineCon2015Options) return capeInfo;

                capeInfo[0] = "MineCon 2015";
                capeInfo[1] = "m15";
                capeInfo[2] = "1";
                return capeInfo;
            }
            case "e7dfea16dc83c97df01a12fabbd1216359c0cd0ea42f9999b6e97c584963e980" -> {
                if(!CapeCacher.config.capes.mineCon2016Options) return capeInfo;

                capeInfo[0] = "MineCon 2016";
                capeInfo[1] = "m16";
                capeInfo[2] = "1";
                return capeInfo;
            }
            case "ca35c56efe71ed290385f4ab5346a1826b546a54d519e6a3ff01efa01acce81" -> {
                if(!CapeCacher.config.capes.cobaltOptions) return capeInfo;

                capeInfo[0] = "Cobalt";
                capeInfo[1] = "cc";
                capeInfo[2] = "2";
                return capeInfo;
            }
            case "3efadf6510961830f9fcc077f19b4daf286d502b5f5aafbd807c7bbffcaca245" -> {
                if(!CapeCacher.config.capes.scrollsChampOptions) return capeInfo;

                capeInfo[0] = "Scrolls Champ";
                capeInfo[1] = "sc";
                capeInfo[2] = "2";
                return capeInfo;
            }
            case "ae677f7d98ac70a533713518416df4452fe5700365c09cf45d0d156ea9396551" -> {
                if(!CapeCacher.config.capes.moderatorOptions) return capeInfo;

                capeInfo[0] = "Moderator";
                capeInfo[1] = "mod";
                capeInfo[2] = "2";
                return capeInfo;
            }
            case "1bf91499701404e21bd46b0191d63239a4ef76ebde88d27e4d430ac211df681e" -> {
                if(!CapeCacher.config.capes.translatorOptions) return capeInfo;

                capeInfo[0] = "Translator";
                capeInfo[1] = "trans";
                capeInfo[2] = "2";
                return capeInfo;
            }
            case "17912790ff164b93196f08ba71d0e62129304776d0f347334f8a6eae509f8a56" -> {
                if(!CapeCacher.config.capes.mapMakerOptions) return capeInfo;

                capeInfo[0] = "Map Maker";
                capeInfo[1] = "map";
                capeInfo[2] = "2";
                return capeInfo;
            }
            case "2262fb1d24912209490586ecae98aca8500df3eff91f2a07da37ee524e7e3cb6" -> {
                if(!CapeCacher.config.capes.translatorCNOptions) return capeInfo;

                capeInfo[0] = "Translator-Chinese";
                capeInfo[1] = "transc";
                capeInfo[2] = "4";
                return capeInfo;
            }
            case "8f120319222a9f4a104e2f5cb97b2cda93199a2ee9e1585cb8d09d6f687cb761" -> {
                if(!CapeCacher.config.capes.mojangOldOptions) return capeInfo;

                capeInfo[0] = "Mojang-Old";
                capeInfo[1] = "mjold";
                capeInfo[2] = "3";
                return capeInfo;
            }
            case "5786fe99be377dfb6858859f926c4dbc995751e91cee373468c5fbf4865e7151" -> {
                if(!CapeCacher.config.capes.mojangOptions) return capeInfo;

                capeInfo[0] = "Mojang";
                capeInfo[1] = "mj";
                capeInfo[2] = "3";
                return capeInfo;
            }
            case "9e507afc56359978a3eb3e32367042b853cddd0995d17d0da995662913fb00f7" -> {
                if(!CapeCacher.config.capes.mojangStudiosOptions) return capeInfo;

                capeInfo[0] = "Mojang Studios";
                capeInfo[1] = "mjs";
                capeInfo[2] = "3";
                return capeInfo;
            }
            case "5048ea61566353397247d2b7d946034de926b997d5e66c86483dfb1e031aee95" -> {
                if(!CapeCacher.config.capes.turtleOptions) return capeInfo;

                capeInfo[0] = "Turtle";
                capeInfo[1] = "t";
                capeInfo[2] = "4";
                return capeInfo;
            }
            case "d8f8d13a1adf9636a16c31d47f3ecc9bb8d8533108aa5ad2a01b13b1a0c55eac" -> {
                if(!CapeCacher.config.capes.prismarineOptions) return capeInfo;

                capeInfo[0] = "Prismarine";
                capeInfo[1] = "p";
                capeInfo[2] = "4";
                return capeInfo;
            }
            case "bcfbe84c6542a4a5c213c1cacf8979b5e913dcb4ad783a8b80e3c4a7d5c8bdac" -> {
                if(!CapeCacher.config.capes.dannyBStyleOptions) return capeInfo;

                capeInfo[0] = "DannyB Style";
                capeInfo[1] = "db";
                capeInfo[2] = "4";
                return capeInfo;
            }
            case "23ec737f18bfe4b547c95935fc297dd767bb84ee55bfd855144d279ac9bfd9fe" -> {
                if(!CapeCacher.config.capes.julianClarkOptions) return capeInfo;

                capeInfo[0] = "Julian Clark";
                capeInfo[1] = "jc";
                capeInfo[2] = "4";
                return capeInfo;
            }
            case "ca29f5dd9e94fb1748203b92e36b66fda80750c87ebc18d6eafdb0e28cc1d05f" -> {
                if(!CapeCacher.config.capes.cheapsh0tOptions) return capeInfo;

                capeInfo[0] = "cheapsh0t";
                capeInfo[1] = "cheap";
                capeInfo[2] = "4";
                return capeInfo;
            }
            case "2e002d5e1758e79ba51d08d92a0f3a95119f2f435ae7704916507b6c565a7da8" -> {
                if(!CapeCacher.config.capes.mrMessiahOptions) return capeInfo;

                capeInfo[0] = "MrMessiah";
                capeInfo[1] = "mm";
                capeInfo[2] = "4";
                return capeInfo;
            }
            case "2056f2eebd759cce93460907186ef44e9192954ae12b227d817eb4b55627a7fc" -> {
                if(!CapeCacher.config.capes.birthdayOptions) return capeInfo;

                capeInfo[0] = "birthday";
                capeInfo[1] = "b";
                capeInfo[2] = "4";
                return capeInfo;
            }
            case "6a7cf0eb5cfe7e7c508b364e32916dfd28d164e7bf6d92c6ea7811b82451e760" -> {
                if(!CapeCacher.config.capes.valentineOptions) return capeInfo;

                capeInfo[0] = "Valentine";
                capeInfo[1] = "val";
                capeInfo[2] = "4";
                return capeInfo;
            }
            case "70efffaf86fe5bc089608d3cb297d3e276b9eb7a8f9f2fe6659c23a2d8b18edf" -> {
                if(!CapeCacher.config.capes.millionthSaleOptions) return capeInfo;

                capeInfo[0] = "Millionth Sale";
                capeInfo[1] = "ms";
                capeInfo[2] = "4";
                return capeInfo;
            }
            case "28de4a81688ad18b49e735a273e086c18f1e3966956123ccb574034c06f5d336" -> {
                capeInfo[0] = "[!] Pancake";
                capeInfo[1] = "bp";
                capeInfo[2] = "6";
                return capeInfo;
            }
            case "99aba02ef05ec6aa4d42db8ee43796d6cd50e4b2954ab29f0caeb85f96bf52a1" -> {
                capeInfo[0] = "[!] Founders Cape";
                capeInfo[1] = "bf";
                capeInfo[2] = "6";
                return capeInfo;
            }
            case "938155dd83118a3993a22579649fab313cdb06073029c3839843d58fad06ebb2" -> {
                capeInfo[0] = "[!] Xbox 360";
                capeInfo[1] = "bx";
                capeInfo[2] = "6";
                return capeInfo;
            }
            default -> {
                if(!CapeCacher.config.capes.unknownCapeOptions) return capeInfo;

                capeInfo[0] = "[?] UNKNOWN CAPE";
                capeInfo[1] = "unknown";
                capeInfo[2] = "0";
                return capeInfo;
            }
        }
    }
}
