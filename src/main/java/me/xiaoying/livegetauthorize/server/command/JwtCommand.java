package me.xiaoying.livegetauthorize.server.command;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.command.Command;
import me.xiaoying.livegetauthorize.core.command.CommandSender;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.lang.JoseException;

import java.util.List;
import java.util.UUID;

public class JwtCommand extends Command {
    public JwtCommand(String name, String description, String usage, List<String> alias) {
        super(name, description, usage, alias);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        String keyId = UUID.randomUUID().toString().replaceAll("-", "");
        RsaJsonWebKey jwk = null;
        try {
            jwk = RsaJwkGenerator.generateJwk(2048);
        } catch (JoseException e) {
            e.printStackTrace();
        }
        jwk.setKeyId(keyId);
        //采用的签名算法 RS256
        jwk.setAlgorithm(AlgorithmIdentifiers.RSA_USING_SHA256);
        String publicKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.PUBLIC_ONLY);
        String privateKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
        LACore.getLogger().info("key: {}", keyId);
        LACore.getLogger().info("publicKey: {}", publicKey);
        LACore.getLogger().info("privateKey: {}", privateKey);
    }
}