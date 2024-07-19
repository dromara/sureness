package org.dromara.sureness.subject.creater;

import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.creater.JwtSubjectServletCreator;
import org.dromara.sureness.util.JsonWebTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @author tomsun28
 * @date 21:04 2020-03-08
 */
public class JwtSubjectServletCreatorTest {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String DEFAULT_SECRET_KEY =
        "MIIEowIBAl+f/dKhaX0csgOCTlCxq20yhmUea6H6JIpST3ST1SE2Rwp" +
            "LnfKefTjsIfJLBa2YkhEqE/GtcHDTNe4CU6+9y/S5z50Kik70LsP43r" +
            "RnLN7XNn4wARoQXizIv6MHUsIV+EFfiMw/x7R0ntu4aWr/CWuApcFaj" +
            "4mWEa6EwrPHTZmbT5Mt45AM2UYhzDHK+0F0rUq3MwH+oXsm+L3F/zjj" +
            "M6EByXIO+SV5+8tVt4bisXQ13rbN0oxhUZR73+LDj9mxa6rFhMW+lfx" +
            "CyaFv0bwq2Eik0jdrKUtsA6bx3sDJeFV643R+YYzGMRIqcBIp6AKA98" +
            "GM2RIqcBIp6-?::4390fsf4sdl6opf)4ZI:tdQMtcQQ14pkOAQdQ546";

    private SubjectCreate creator;

    @BeforeEach
    public void setUp() {
        creator = new JwtSubjectServletCreator();
        JsonWebTokenUtil.setDefaultSecretKey(DEFAULT_SECRET_KEY);
    }


    @Test
    public void canSupportSubject() {
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, Arrays.asList("role2", "rol3"),
                null, Boolean.FALSE);
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        expect(request.getHeader(AUTHORIZATION)).andReturn(BEARER + " " + jwt);
        replay(request);
        assertTrue(creator.canSupportSubject(request));
        verify(request);
    }

    @Test
    public void createSubject() {
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, Arrays.asList("role2", "rol3"),
                null, Boolean.FALSE);
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        expect(request.getHeader(AUTHORIZATION)).andReturn(BEARER + " " + jwt);
        expect(request.getServletPath()).andReturn("/api/v1/book");
        expect(request.getMethod()).andReturn("put");
        expect(request.getRemoteHost()).andReturn("192.167.2.1");
        replay(request);
        assertNotNull(creator.createSubject(request));
        verify(request);
    }
}