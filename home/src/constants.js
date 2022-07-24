import React from 'react'

import Translate, {translate} from '@docusaurus/Translate'


export const features = [{
    title: translate({
        message: 'Extendable'
    }),
    description: (
        <>
            <Translate values={{
                restApi: <strong>REST API</strong>,
                rbac: <strong>RBAC</strong>,
                authentication: <strong>authentication</strong>,
                authorization: <strong>authorization</strong>,
                simple: <strong>simple</strong>,
                useful: <strong>useful</strong>,
                interface: <strong>扩展接口</strong>,
                sample: <strong>样例</strong>,
                doc: <strong>文档</strong>,
                br: <br/>
            }}>
                {'Focus on the protection of {restApi}. Based on {rbac}.{br}' +
                'Provides {authentication} and {authorization}, etc.{br}' +
                'Extension custom interface is {simple} and really {useful}.'
                }
            </Translate>
        </>
    ),
}, {
    title: translate({
        message: 'Compatible'
    }),
    description: (
        <>
            <Translate values={{
                WebSockets: <strong>WebSockets</strong>,
                Servlet: <strong>Servlet</strong>,
                JaxRs: <strong>JAX-RS</strong>,
                native: <strong>Native</strong>,
                SpringBoot: <strong>Spring Boot</strong>,
                SpringWebFlux: <strong>Spring WebFlux</strong>,
                Javalin: <strong>Javalin</strong>,
                Quarkus: <strong>Quarkus</strong>,
                Ktor: <strong>Ktor</strong>,
                Micronaut: <strong>Micronaut</strong>,
                Solon: <strong>Solon</strong>,
                Jfinal: <strong>Jfinal</strong>,
                br: <br/>
            }}>
                {'Support {WebSockets}, HTTP containers ({Servlet} and {JaxRs}).{br}' +
                'No framework dependency.' +
                '{native} supports {SpringBoot}, {SpringWebFlux}, {Javalin}, {Quarkus},' +
                '{Ktor}, {Solon}, {Jfinal}, {Micronaut} etc.'
                }
            </Translate>
        </>
    ),
},
{
    title: translate({
        message: 'Multi And Fast'
    }),
    description: (
        <>
            <Translate values={{
                jwt: <strong>JWT</strong>,
                basic: <strong>Basic Auth</strong>,
                digest: <strong>Digest Auth</strong>,
                dynamic: <strong>Dynamic</strong>,
                perm: <strong>permissions</strong>,
                dynamicCn: <strong>动态修改权限</strong>,
                high: <strong>High performance</strong>,
                highCn: <strong>高性能</strong>,
                tree: <strong>Dictionary Matching Tree</strong>,
                treeCn: <strong>改进的字典匹配树</strong>,
                br: <br/>
            }}>
                {'Supports {jwt}, {basic}, {digest} etc.{br}' +
                '{dynamic} modification of {perm}.{br}' +
                '{high} with {tree}.'
                }
            </Translate>
        </>
    ),
}]

export const friendLinks = [
    {
        img: 'maxkey_logo.png',
        alt: 'MaxKey',
        url: 'https://maxkey.top/'
    }, {
        img: 'tlog_logo.png',
        alt: 'TLog',
        url: 'https://yomahub.com/tlog/'
    }, {
        img: 'hutool_logo.jpg',
        alt: 'Hutool',
        url: 'https://hutool.cn/'
    }, {
        img: 'satoken_logo.png',
        alt: 'Sa-Token',
        url: 'http://sa-token.dev33.cn/'
    }, {
        img: 'liteflow_logo.png',
        alt: 'LiteFlow',
        url: 'https://yomahub.com/liteflow/'
    }, {
        img: 'jpom_logo.png',
        alt: 'jpom',
        url: 'https://jpom.io/'
    }, {
        img: 'fast_request_logo.png',
        alt: 'fast-request',
        url: 'https://plugins.sheng90.wang/fast-request/'
    }, {
        img: 'justauth_logo.png',
        alt: 'Justauth',
        url: 'https://justauth.wiki/'
    }, {
        img: 'pha_logo.jfif',
        alt: 'pha_api',
        url: 'https://www.phalapi.net/'
    }, {
        img: 'hertzbeat_logo.svg',
        alt: 'hertzbeat',
        url: 'https://hertzbeat.com/'
    }, {
        img: 'koalas_rpc_logo.png',
        alt: 'koalas_rpc',
        url: 'https://gitee.com/dromara/koalas-rpc'
    }, {
        img: 'liteflow_logo.png',
        alt: 'LiteFlow',
        url: 'https://yomahub.com/liteflow/'
    }, {
        img: 'hmily_logo.png',
        alt: 'hmily',
        url: 'https://dromara.org/'
    }, {
        img: 'forest_logo.png',
        alt: 'Forest',
        url: 'https://forest.dtflyx.com/'
    }, {
        img: 'cubic_logo.png',
        alt: 'cubic',
        url: 'https://cubic.jiagoujishu.com/'
    }, {
        img: 'raincat_logo.png',
        alt: 'Raincat',
        url: 'https://dromara.org/'
    }, {
        img: 'easy_es_logo.png',
        alt: 'easy-es',
        url: 'https://www.easy-es.cn/'
    }, {
        img: 'dynamic_tp_logo.png',
        alt: 'dynamic-tp',
        url: 'https://dynamictp.cn/'
    }, {
        img: 'northstar_logo.png',
        alt: 'northstar',
        url: 'https://gitee.com/dromara/northstar'
    }, {
        img: 'mendmix_logo.png',
        alt: 'mendmix',
        url: 'https://www.jeesuite.com/'
    }, {
        img: 'gobrs_logo.png',
        alt: 'Gobrs-Async',
        url: 'https://async.sizegang.cn/'
    }, {
        img: 'easypdf_logo.png',
        alt: 'easypdf',
        url: 'https://dromara.gitee.io/x-easypdf/'
    },
]

export const mediaPartners = [
    {
        img: 'dromara_qr.png',
        alt: 'Dromara',
        url: 'https://mp.weixin.qq.com/s/Q3b7ZE802IMF6MwIPJIGQA'
    },
    {
        img: 'JavaHouDuan_logo.png',
        alt: 'JavaHouDuan',
        url: 'https://mp.weixin.qq.com/s/Ylq51a7Av8ZRuH811xZnDA'
    }
]

export const logos = [{
/**
 * Page 1
 */
    img: 'justauth_logo.png',
    alt: 'Justauth',
    url: 'https://justauth.wiki/'
}, {
    img: 'maxKey_logo.png',
    alt: 'MaxKey',
    url: 'https://maxkey.top/'
}, {
    img: 'pha_logo.jfif',
    alt: 'pha_api',
    url: 'https://www.phalapi.net/'
}
// , {
//     img: 'mozilla.png',
//     alt: 'Mozilla',
//     url: 'https://www.mozilla.org/'
// }, {
//     img: 'buoyant.png',
//     alt: 'Buoyant',
//     url: 'https://buoyant.io/'
// }, {
//     img: 'sap.png',
//     alt: 'SAP',
//     url: 'https://www.sap.com/'
// },
/**
 * Page 2
 */
// {
//     img: 'hilton.png',
//     alt: 'Hilton',
//     url: 'https://www.hilton.com/'
// }, {
//     img: 'schwab.png',
//     alt: 'Charles Schwab',
//     url: 'https://www.schwab.com/'
// }, {
//     img: 'jwplayer.png',
//     alt: 'JW Player',
//     url: 'https://www.jwplayer.com/'
// }, {
//     img: 'bbva.png',
//     alt: 'BBVA',
//     url: 'https://www.bbva.com/'
// }, {
//     img: 'gopro.png',
//     alt: 'GoPro',
//     url: 'https://gopro.com/'
// }, {
//     img: 'algolia.png',
//     alt: 'Algolia',
//     url: 'https://www.algolia.com/'
// },
// /**
//  * Page 3
//  */
// {
//     img: 'financialtimes.png',
//     alt: 'Financial Times',
//     url: 'https://www.ft.com/'
// }, {
//     img: 'zendesk.png',
//     alt: 'Zendesk',
//     url: 'https://www.zendesk.com/'
// }, {
//     img: '1und1.png',
//     alt: '1&1',
//     url: 'https://www.1und1.de/'
// }, {
//     img: 'avira.png',
//     alt: 'Avira',
//     url: 'https://www.avira.com/'
// }, {
//     img: 'deloitte.jpg',
//     alt: 'Deloitte',
//     url: 'https://deloitte.com'
// }, {
//     img: 'rabobank.png',
//     alt: 'Rabobank',
//     url: 'https://www.rabobank.com/'
// }
]


export const SetupExample = `
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>1.0.6</version>
</dependency>

compile group: 'com.usthe.sureness', name: 'sureness-core', version: '1.0.6'
`

export const SurenessIntegration = `
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(servletRequest);
        } catch (IncorrectCredentialsException | UnknownAccountException | ExpiredCredentialsException e1) {
            logger.debug("this request account info is illegal");
            responseWrite(ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED).body(e1.getMessage()), servletResponse);
            return;
        } catch (UnauthorizedException e4) {
            logger.debug("this account can not access this resource");
            responseWrite(ResponseEntity
                    .status(HttpStatus.FORBIDDEN).body(e4.getMessage()), servletResponse);
            return;
        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            responseWrite(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(),
                    servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
`
