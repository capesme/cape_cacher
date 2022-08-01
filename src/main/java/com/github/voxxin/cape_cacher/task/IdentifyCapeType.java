package com.github.voxxin.cape_cacher.task;

import java.net.URL;

public class IdentifyCapeType {

    public static String CapeIdentifier(String CapeURL) {
        if (CapeURL.contains("http://textures.minecraft.net/texture/")) {
            if (CapeURL.equals("http://textures.minecraft.net/texture/953cac8b779fe41383e675ee2b86071a71658f2180f56fbce8aa315ea70e2ed6")) {
                return "MineCon 2011";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/a2e8d97ec79100e90a75d369d1b3ba81273c4f82bc1b737e934eed4a854be1b6")) {
                return "MineCon 2012";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/153b1a0dfcbae953cdeb6f2c2bf6bf79943239b1372780da44bcbb29273131da")) {
                return "MineCon 2013";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/b0cc08840700447322d953a02b965f1d65a13a603bf64b17c803c21446fe1635")) {
                return "MineCon 2015";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/e7dfea16dc83c97df01a12fabbd1216359c0cd0ea42f9999b6e97c584963e980")) {
                return "MineCon 2016";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/3efadf6510961830f9fcc077f19b4daf286d502b5f5aafbd807c7bbffcaca245")) {
                return "§bScrolls Champ";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/ca35c56efe71ed290385f4ab5346a1826b546a54d519e6a3ff01efa01acce81")) {
                return "§bCobalt";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/ae677f7d98ac70a533713518416df4452fe5700365c09cf45d0d156ea9396551")) {
                return "§bModerator";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/1bf91499701404e21bd46b0191d63239a4ef76ebde88d27e4d430ac211df681e")) {
                return "§bTranslator";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/17912790ff164b93196f08ba71d0e62129304776d0f347334f8a6eae509f8a56")) {
                return "§bMap Maker";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/2262fb1d24912209490586ecae98aca8500df3eff91f2a07da37ee524e7e3cb6")) {
                return "§bTranslator-Chinese";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/8f120319222a9f4a104e2f5cb97b2cda93199a2ee9e1585cb8d09d6f687cb761")) {
                return "§4Mojang-Old";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/5786fe99be377dfb6858859f926c4dbc995751e91cee373468c5fbf4865e7151")) {
                return "§4Mojang";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/9e507afc56359978a3eb3e32367042b853cddd0995d17d0da995662913fb00f7")) {
                return "§4Mojang Studios";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/5048ea61566353397247d2b7d946034de926b997d5e66c86483dfb1e031aee95")) {
                return "§dTurtle";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/d8f8d13a1adf9636a16c31d47f3ecc9bb8d8533108aa5ad2a01b13b1a0c55eac")) {
                return "§dPrismarine";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/bcfbe84c6542a4a5c213c1cacf8979b5e913dcb4ad783a8b80e3c4a7d5c8bdac")) {
                return "§dDannyB Style";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/23ec737f18bfe4b547c95935fc297dd767bb84ee55bfd855144d279ac9bfd9fe")) {
                return "§dJulian Clark";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/ca29f5dd9e94fb1748203b92e36b66fda80750c87ebc18d6eafdb0e28cc1d05f")) {
                return "§dcheapsh0t";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/2e002d5e1758e79ba51d08d92a0f3a95119f2f435ae7704916507b6c565a7da8")) {
                return "§dMrMessiah";
            } else if (CapeURL.equals("https://textures.minecraft.net/texture/2056f2eebd759cce93460907186ef44e9192954ae12b227d817eb4b55627a7fc")) {
                return "§dBirthday";
            } else if (CapeURL.equals("http://textures.minecraft.net/texture/70efffaf86fe5bc089608d3cb297d3e276b9eb7a8f9f2fe6659c23a2d8b18edf")) {
                return "§dMillionth Sale";
            } else if (CapeURL.equals("https://textures.minecraft.net/texture/28de4a81688ad18b49e735a273e086c18f1e3966956123ccb574034c06f5d336")) {
                    return "§6[BEDROCK] Pancake";
            } else if (CapeURL.equals("https://textures.minecraft.net/texture/99aba02ef05ec6aa4d42db8ee43796d6cd50e4b2954ab29f0caeb85f96bf52a1")) {
                return "§6[BEDROCK] Founders cape";
            } else if (CapeURL.equals("https://textures.minecraft.net/texture/938155dd83118a3993a22579649fab313cdb06073029c3839843d58fad06ebb2")) {
                return "§6[BEDROCK] Xbox 360";
            } else {
                return "UNKNOWN CAPE";
            }
        } else {
            return "Not a vanilla cape!";
        }
    }
}
