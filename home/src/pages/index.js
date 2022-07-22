import React from 'react'
import clsx from 'clsx'
import Layout from '@theme/Layout'
import CodeBlock from '@theme/CodeBlock'
import Link from '@docusaurus/Link'
import useDocusaurusContext from '@docusaurus/useDocusaurusContext'
import useBaseUrl from '@docusaurus/useBaseUrl'
import Translate, {translate} from '@docusaurus/Translate';

import Feature from './components/Feature'
import Section from './components/Section'
import Highlight from './components/Highlight'
import LogoCarousel from './components/LogoCarousel'
import cdnTransfer from '../CdnTransfer'

import styles from './styles.module.css'
import { features, SetupExample, SurenessIntegration, friendLinks, mediaPartners } from '../constants'

function Home() {
    const context = useDocusaurusContext()
    const {siteConfig = {}} = context
    return (
        <Layout
            title={`${siteConfig.title} · ${siteConfig.tagline}`}
            description={`${siteConfig.tagline}`}>
            <header className={clsx('hero hero--primary', styles.heroBanner)}>
                <div className="container">
                    <h1 className="hero__title">
                        <img src={cdnTransfer('img/brand128.svg')}/>
                    </h1>
                    <p className="hero__subtitle"><Translate>Focus on Protection of API</Translate></p>
                    <div className={styles.social}>
                        <a href="https://www.apache.org/licenses/LICENSE-2.0.html"><img
                            src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg"/></a>
                        <a href="https://search.maven.org/artifact/com.usthe.sureness/sureness-core"><img
                            src="https://img.shields.io/badge/Maven%20Central-1.0.6-blue.svg"/></a>
                        <a href="https://www.apache.org/licenses/LICENSE-2.0.html"><img
                            src="https://img.shields.io/github/release-date/dromara/sureness?color=blue&logo=figshare&logoColor=red"/></a>
                        <a href="https://img.shields.io/github/status/contexts/pulls/dromara/sureness/8?label=pull%20checks"><img
                            src="https://img.shields.io/github/status/contexts/pulls/dromara/sureness/8?label=pull%20checks"/></a>
                    </div>
                    <div className={styles.buttons}>
                        <Link
                            className={clsx(
                                'button button--outline button--secondary button--lg',
                                styles.getStarted,
                            )}
                            to={useBaseUrl('docs/')}>
                            <Translate>Get Started</Translate>
                        </Link>
                        <Link
                            to="https://github.com/dromara/sureness"
                            className={clsx(
                                'button button--outline button--secondary button--lg',
                                styles.getStarted,
                            )}
                        >Github</Link>
                        <Link
                            to="https://gitee.com/dromara/sureness"
                            className={clsx(
                                'button button--outline button--secondary button--lg',
                                styles.getStarted,
                            )}
                        >Gitee</Link>
                    </div>
                </div>
            </header>
            <main>
                {features && features.length > 0 && (
                    <Section isDark>
                        {features.map((props, idx) => (
                            <Feature key={idx} {...props} />
                        ))}
                    </Section>
                )}
                {/*who is using*/}
                {/*<Section>*/}
                {/*    <LogoCarousel logos={logos}></LogoCarousel>*/}
                {/*</Section>*/}
                <Highlight
                    img={
                        <CodeBlock className="js" children={SurenessIntegration}></CodeBlock>
                    }
                    reversed
                    title={
                        translate({
                            message: 'Support for Jvm Modern Frameworks'
                        })
                    }
                    text={
                        <>
                            <p>
                                <Translate values={{
                                    Spring: <a href="https://spring.io/">Spring</a>,
                                    SpringBoot: <a href="https://spring.io/">Spring Boot</a>,
                                    SpringWebFlux: <a href="https://spring.io">Spring WebFlux</a>,
                                    Javalin: <a href="https://javalin.io/">Javalin</a>,
                                    Quarkus: <a href="https://quarkus.io/">Quarkus</a>,
                                    Micronaut: <a href="https://micronaut.io/">Micronaut</a>,
                                    Solon: <a href="https://gitee.com/noear/solon">Solon</a>,
                                    Jfinal: <a href="https://jfinal.com/">Jfinal</a>,
                                    Ktor: <a href="https://ktor.io/">Ktor</a>,
                                    br: <br/>
                                }}>
                                    {'Sureness allows you to security any server written with jvm modern frameworks such as {Spring}, {SpringBoot}, {SpringWebFlux}, {Javalin}, ' +
                                    '{Quarkus}, {Micronaut}, {Solon}, {Jfinal} or {Ktor} as well as frameworks for Kotlin.'
                                    }
                                </Translate>
                            </p>
                            <p>
                                <Translate values={{
                                    interceptor: <strong>interceptor</strong>,
                                    handling: <strong>Exception Handling Process</strong>,
                                    checkIn: <code>checkIn()</code>,
                                    SubjectSum: <strong>SubjectSum</strong>,
                                    br: <br/>
                                }}>
                                    {'The essence of Sureness is to use {interceptor}(like servlet filter or Spring interceptor)  to intercept all rest requests for authenticating and authorizing.{br}' +
                                    'So no matter any framework, as long as it has a interceptor, it can integrate with sureness. ' +
                                    'Sureness uses {handling}, {checkIn} will return {SubjectSum}(user information) when auth success, or throw different types of auth exceptions when auth error.'
                                    }
                                </Translate>
                            </p>
                            <div>
                                <h4>Native Support for:</h4>
                                <a href="https://spring.io/" className={styles.frameworkLogos}><img
                                    src={cdnTransfer('img/icons/spring-logo.svg')} alt="spring"/></a>
                                <a href="https://javalin.io/" className={styles.frameworkLogos}><img
                                    src={cdnTransfer('img/icons/javalin_logo.svg')} alt="Javalin"/></a>
                                <a href="https://micronaut.io/" className={styles.frameworkLogos}><img
                                    src={cdnTransfer('img/icons/micronaut_logo.png')} alt="Micronaut"/></a>
                                <a href="https://quarkus.io/" className={styles.frameworkLogos}><img
                                    src={cdnTransfer('img/icons/quarkus_logo.svg')} alt="Quarkus"/></a>
                                <a href="https://ktor.io/" className={styles.frameworkLogos}><img
                                    src={cdnTransfer('img/icons/ktor_logo.svg')} alt="Ktor"/></a>
                            </div>
                        </>
                    }
                />
                <Highlight
                    img={
                        <img
                            width="760"
                            height="445"
                            src={cdnTransfer('img/compare.png')}
                        />
                    }
                    isDark
                    title={translate({
                        message: 'Multi Support Samples'
                    })}
                    // title="Multi Support Samples"
                    text={
                        <>
                            <p>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/sample-bootstrap"> Spring Boot
                                sample(configuration file scheme)</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/sample-tom"> Spring Boot
                                sample(database scheme)</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/samples/quarkus-sureness"> Quarkus
                                sample</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/samples/javalin-sureness"> Javalin
                                sample</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/samples/ktor-sureness"> Ktor
                                sample</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/samples/spring-webflux-sureness"> Spring
                                Webflux sample</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/samples/micronaut-sureness"> Micronaut
                                sample</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/samples/jfinal-sureness"> Jfinal
                                sample</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/samples/solon-sureness"> Solon
                                sample</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/samples/spring-gateway-sureness"> Spring
                                Gateway sample</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/samples/zuul-sureness"> Zuul
                                sample</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/samples/sureness-session"> Session
                                sample</a><br/>
                                Sureness integrate <a
                                href="https://github.com/dromara/sureness/tree/master/samples/sureness-redis-session"> Redis
                                Session cache sample</a><br/>
                            </p>
                        </>
                    }
                />
                <Highlight
                    img={
                        <img
                            width="560"
                            height="415"
                            src={cdnTransfer('img/benchmark_en.png')}
                        />
                    }
                    reversed
                    title={
                        translate({
                            message: 'Benchmark Compare'
                        })
                    }
                    text={
                        <>
                            <p>
                                <Translate values={{
                                    Benchmark: <a
                                        href="https://github.com/tomsun28/sureness-shiro-spring-security-benchmark">Benchmark</a>,
                                    times3: <strong>3 times </strong>,
                                    times4: <strong>4 times </strong>,
                                    times3cn: <strong>3 倍 </strong>,
                                    times4cn: <strong>4 倍 </strong>,
                                    increases: <strong>The performance gap will be further widened as the api matching
                                        chain increases</strong>,
                                    increaseCn: <strong>性能差距会随着api匹配链的增加而进一步拉大</strong>,
                                    br: <br/>
                                }}>
                                    {'{Benchmark} test shows Sureness to lose 0.026ms performance compared to frameless application, Shiro lose 0.088ms, Spring Security lose 0.116ms.{br}' +
                                    'In contrast, Sureness basically does not consume performance, and the performance (TPS loss) is {times3} that of Shiro and {times4} that of Spring Security.{br}' +
                                    '{increases}.'}
                                </Translate>
                            </p>
                            <p>
                                <code>ab -n 4000 -c 50 -A root:23456 localhost:8088/api/v1/source1</code><br/>
                                Detail see <a
                                href="https://github.com/tomsun28/sureness-shiro-spring-security-benchmark">Benchmark
                                Test</a>
                            </p>
                        </>
                    }
                />
                <Highlight
                    img={
                        <img
                            width="760"
                            height="405"
                            src={cdnTransfer('img/PathRoleMatcher.svg')}
                        />
                    }
                    isDark
                    title={
                        translate({
                            message: 'Why Is High Performance'
                        })
                    }
                    text={
                        <>
                            <p>
                                <Translate values={{
                                    filter: <strong>filter chain</strong>,
                                    linear: <strong>linear matching</strong>,
                                    linearCn: <strong>线性过滤链匹配</strong>,
                                    tree: <strong>dictionary matching tree</strong>,
                                    treeCn: <strong>改进的字典匹配树</strong>,
                                    ant: <strong>linear ant matching</strong>,
                                    antCn: <strong>线性Ant匹配</strong>,
                                    effective: <strong>effective</strong>,
                                    effCn: <strong>较大性能</strong>,
                                    br: <br/>
                                }}>
                                    {'In a large number of requests, we found that the {linear} of the {filter} is a performance bottleneck. {br}' +
                                    'So we used a {tree} instead of {ant}.{br}' +
                                    'Practice has proved that it is very {effective}.'}
                                </Translate>
                            </p>
                        </>
                    }
                />
                <Highlight
                    img={
                        <CodeBlock className="bash" children={SetupExample}></CodeBlock>
                    }
                    reversed
                    title={
                        translate({
                            message: 'Get Started With Sureness within Minutes'
                        })
                    }
                    text={
                        <>
                            <p>
                                <Translate values={{
                                    br: <br/>
                                }}>
                                    {'We provide many tutorials and samples, you can refer to them to build' +
                                    'a complete permission project within 10 minutes.{br}' +
                                    'Have Fun!'}
                                </Translate>
                            </p>
                        </>
                    }
                />
                {/*Friend Links*/}
                <Section>
                    <LogoCarousel logos={friendLinks} headerTitle={translate({message: 'Friend Links'})}></LogoCarousel>
                </Section>
                {/*Media Partners*/}
                {/*<Section>*/}
                {/*    <LogoCarousel logos={mediaPartners} headerTitle={translate({message: 'Media Partners'})}></LogoCarousel>*/}
                {/*</Section>*/}
            </main>
        </Layout>
    )
}

export default Home
