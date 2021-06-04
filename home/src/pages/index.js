import React from 'react'
import clsx from 'clsx'
import Layout from '@theme/Layout'
import CodeBlock from '@theme/CodeBlock'
import Link from '@docusaurus/Link'
import useDocusaurusContext from '@docusaurus/useDocusaurusContext'
import useBaseUrl from '@docusaurus/useBaseUrl'

import LogoCarousel from './components/LogoCarousel'
import Feature from './components/Feature'
import Section from './components/Section'
import Highlight from './components/Highlight'
import Robot from './components/Robot'

import styles from './styles.module.css'
import { logos, features, LHIntregrationExample, SetupExample, ReactIntegration, SurenessIntegration } from '../constants'

function Home() {
    const context = useDocusaurusContext()
    const { siteConfig = {} } = context
    return (
        <Layout
            title={`${siteConfig.title} · ${siteConfig.tagline}`}
            description={`${siteConfig.tagline}`}>
            <header className={clsx('hero hero--primary', styles.heroBanner)}>
                <div className="container">
                    <h1 className="hero__title">
                        <img src="/img/brand128.svg"/>
                    </h1>
                    <p className="hero__subtitle">{siteConfig.tagline}</p>
                    <div className={styles.social}>
                        <a href="https://www.apache.org/licenses/LICENSE-2.0.html"><img src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg"/></a>
                        <a href="https://search.maven.org/artifact/com.usthe.sureness/sureness-core"><img src="https://img.shields.io/badge/Maven%20Central-1.0.3-blue.svg"/></a>
                        <a href="https://www.apache.org/licenses/LICENSE-2.0.html"><img src="https://img.shields.io/github/release-date/dromara/sureness?color=blue&logo=figshare&logoColor=red"/></a>
                        <a href="https://img.shields.io/github/status/contexts/pulls/dromara/sureness/8?label=pull%20checks"><img src="https://img.shields.io/github/status/contexts/pulls/dromara/sureness/8?label=pull%20checks"/></a>
                    </div>
                    <div className={styles.buttons}>
                        <Link
                            className={clsx(
                                'button button--outline button--secondary button--lg',
                                styles.getStarted,
                            )}
                            to={useBaseUrl('docs/')}>
                        Get Started
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
                    title="Support for Jvm Modern Frameworks"
                    text={
                        <>
                            <p>
                                Sureness allows you to security any server written with jvm modern frameworks such as <a href="https://reactjs.org/">Spring</a>, <a href="https://angular.io/">Spring Boot</a>, <a href="https://www.polymer-project.org/">Javalin</a>,
                                <a href="https://www.polymer-project.org/">Quarkus</a> or <a href="https://vuejs.org/">Ktor</a> as well as frameworks for Kotlin.
                            </p>
                            <p>
                                The essence of Sureness is to use <strong>interceptor</strong>(like servlet filter or Spring interceptor)  to intercept all rest requests for authenticating and authorizing.<br/>
                                So no matter any framework, as long as it has a interceptor, it can integrate sureness.
                                Sureness uses <strong>exception handling process</strong>, <strong>checkIn()</strong> will return a <strong>SubjectSum</strong>(user information) when auth success, or throw different types of auth exceptions when auth error.
                            </p>
                            <div>
                                <h4>Native Support for:</h4>
                                <a href="https://reactjs.org/" className={styles.frameworkLogos}><img src="/img/icons/spring-logo.svg" alt="spring" /></a>
                                <a href="https://www.polymer-project.org/" className={styles.frameworkLogos}><img src="/img/icons/javalin_logo.svg" alt="Javalin" /></a>
                                <a href="https://angular.io/" className={styles.frameworkLogos}><img src="/img/icons/MicronautLogo.svg" alt="Micronaut" /></a>
                                <a href="https://vuejs.org/" className={styles.frameworkLogos}><img src="/img/icons/quarkus_logo.svg" alt="Quarkus" /></a>
                                <a href="https://angular.io/" className={styles.frameworkLogos}><img src="/img/icons/ktor_logo.svg" alt="Ktor" /></a>
                            </div>
                        </>
                    }
                />
                <Highlight
                    img={
                        <img
                            width="760"
                            height="445"
                            src="/img/compare.png"
                        />
                    }
                    isDark
                    title="Multi Support Samples"
                    text={
                        <>
                            <p>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/sample-bootstrap"> Spring Boot sample(configuration file scheme)</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/sample-tom"> Spring Boot sample(database scheme)</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/samples/quarkus-sureness"> Quarkus sample</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/samples/javalin-sureness"> Javalin sample</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/samples/ktor-sureness"> Ktor sample</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/samples/spring-webflux-sureness"> Spring Webflux sample</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/samples/micronaut-sureness"> Micronaut sample</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/samples/jfinal-sureness"> Jfinal sample</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/samples/solon-sureness"> Solon sample</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/samples/spring-gateway-sureness"> Spring Gateway sample</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/samples/zuul-sureness"> Zuul sample</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/samples/sureness-session"> Session sample</a><br/>
                                Sureness integration <a href="https://github.com/dromara/sureness/tree/master/samples/sureness-redis-session"> Redis Session cache sample</a><br/>
                            </p>
                        </>
                    }
                />
                <Highlight
                    img={
                        <img
                            width="560"
                            height="415"
                            src="/img/benchmark_en.png"
                        />
                    }
                    reversed
                    title="Benchmark Compare"
                    text={
                        <>
                            <p>
                                <a href="https://github.com/tomsun28/sureness-shiro-spring-security-benchmark">Benchmark</a> test shows
                                Sureness to lose 0.026ms performance compared to frameless application, Shiro lose 0.088ms, Spring Security lose 0.116ms.<br/>
                                In contrast, Sureness basically does not consume performance, and the performance (TPS loss) is 3 times that of Shiro and 4 times that of Spring Security.
                                The performance gap will be further widened as the api matching chain increases.
                            </p>
                            <p>
                                <code>ab -n 4000 -c 50 -A root:23456 localhost:8088/api/v1/source1</code><br/>
                                Detail see <a href="https://github.com/tomsun28/sureness-shiro-spring-security-benchmark">Benchmark Test</a>
                            </p>
                        </>
                    }
                />
                <Highlight
                    img={
                        <img
                            width="760"
                            height="405"
                            src="/img/PathRoleMatcher.svg"
                        />
                    }
                    isDark
                    title="Why Is High Performance"
                    text={
                        <>
                            <p>
                                In a large number of requests, we found that the matching of the filter chain is a performance bottleneck.
                                So we used a dictionary matching tree instead of linear ant matching.
                                Practice has proved that it is very effective.
                            </p>
                        </>
                    }
                />
                <Highlight
                    img={
                        <CodeBlock className="bash" children={SetupExample}></CodeBlock>
                    }
                    reversed
                    title="Get Started With Sureness within Minutes"
                    text={
                        <>
                            <p>
                                We provide many tutorials and samples, you can refer to them to build
                                a complete permission project within 10 minutes.<br/>
                                Have Fun!
                            </p>
                        </>
                    }
                />
            </main>
        </Layout>
    )
}

export default Home
