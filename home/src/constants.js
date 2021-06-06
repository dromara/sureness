import React from 'react'

import Translate, {translate} from '@docusaurus/Translate';

export const features = [{
    title: translate({
        message: 'Extendable'
    }),
    description: (
        <>
            Focus on the protection of <strong>REST API</strong>. Based on <strong>RBAC</strong>.<br/>
            Provides <strong>authentication</strong> and <strong>authorization</strong>, etc.<br/>
            Extension custom interface is <strong>simple</strong> and really <strong>useful</strong>.
        </>
    ),
}, {
    title: translate({
        message: 'Compatible'
    }),
    description: (
        <>
            Support <strong>WebSockets</strong>, HTTP containers (<strong>Servlet</strong> and <strong>JAX-RS</strong>).
            No Web framework dependency,
            <strong>native</strong> supports <strong>Spring Boot</strong>, <strong>Javalin</strong>, <strong>Quarkus</strong>,
            <strong>Ktor</strong>, <strong>Micronaut</strong> and more.
        </>
    ),
},
{
    title: translate({
        message: 'Multi And Fast'
    }),
    description: (
        <>
            Supports <strong>JWT</strong>, <strong>Basic Auth</strong>, <strong>Digest Auth</strong> etc.<br/>
            <strong>Dynamic</strong> modification of <strong>permissions</strong>.<br/>
            <strong>High performance</strong> with <strong>Dictionary Matching Tree</strong>.
        </>
    ),
}]

export const logos = [{
/**
 * Page 1
 */
    img: 'google.png',
    alt: 'Google',
    url: 'https://developers.google.com/blockly/'
}, {
    img: 'netflix.png',
    alt: 'Netflix',
    url: 'https://netflix.com/'
}, {
    img: 'microsoft.svg',
    alt: 'Microsoft',
    url: 'https://www.microsoft.com/'
}, {
    img: 'mozilla.png',
    alt: 'Mozilla',
    url: 'https://www.mozilla.org/'
}, {
    img: 'buoyant.png',
    alt: 'Buoyant',
    url: 'https://buoyant.io/'
}, {
    img: 'sap.png',
    alt: 'SAP',
    url: 'https://www.sap.com/'
},
/**
 * Page 2
 */
{
    img: 'hilton.png',
    alt: 'Hilton',
    url: 'https://www.hilton.com/'
}, {
    img: 'schwab.png',
    alt: 'Charles Schwab',
    url: 'https://www.schwab.com/'
}, {
    img: 'jwplayer.png',
    alt: 'JW Player',
    url: 'https://www.jwplayer.com/'
}, {
    img: 'bbva.png',
    alt: 'BBVA',
    url: 'https://www.bbva.com/'
}, {
    img: 'gopro.png',
    alt: 'GoPro',
    url: 'https://gopro.com/'
}, {
    img: 'algolia.png',
    alt: 'Algolia',
    url: 'https://www.algolia.com/'
},
/**
 * Page 3
 */
{
    img: 'financialtimes.png',
    alt: 'Financial Times',
    url: 'https://www.ft.com/'
}, {
    img: 'zendesk.png',
    alt: 'Zendesk',
    url: 'https://www.zendesk.com/'
}, {
    img: '1und1.png',
    alt: '1&1',
    url: 'https://www.1und1.de/'
}, {
    img: 'avira.png',
    alt: 'Avira',
    url: 'https://www.avira.com/'
}, {
    img: 'deloitte.jpg',
    alt: 'Deloitte',
    url: 'https://deloitte.com'
}, {
    img: 'rabobank.png',
    alt: 'Rabobank',
    url: 'https://www.rabobank.com/'
}]

export const LHIntregrationExample = `
browser.emulateDevice('iPhone X')
browser.enablePerformanceAudits({
    networkThrottling: 'Good 3G',
    cacheEnabled: true,
    formFactor: 'mobile'
})

// open application under test
browser.url('https://localhost:3000')

expect(browser.getMetrics().firstMeaningfulPaint)
    .toBeBelow(2500)

const pwaCheckResult = browser.checkPWA()
expect(pwaCheckResult.passed).toBe(true)
`

export const SetupExample = `
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>1.0.3</version>
</dependency>

compile group: 'com.usthe.sureness', name: 'sureness-core', version: '1.0.3'
`

export const ReactIntegration = `
browser.url('https://ahfarmer.github.io/calculator/');
const appWrapper = browser.$('div#root')

browser.react$('t', {
    props: { name: '7' }
}).click()
browser.react$('t', {
    props: { name: 'x' }
}).click()
browser.react$('t', {
    props: { name: '6' }
}).click()
browser.react$('t', {
    props: { name: '=' }
}).click()

// prints "42"
console.log($('.component-display').getText());`

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