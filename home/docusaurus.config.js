const path = require('path')

const organizationName = 'dromara' // Usually your GitHub org/user name.
const projectName = 'sureness' // Usually your repo name.
const branch = 'master'
const repoUrl = `https://github.com/dromara/${projectName}`
const cdnUrl = 'https://cdn.jsdelivr.net/gh/usthe/sureness@gh-pages/'

module.exports = {
    title: 'Sureness',
    tagline: 'Focus on Protection of API',
    url: 'https://su.usthe.com',
    baseUrl: '/',
    onBrokenLinks: 'throw',
    onBrokenMarkdownLinks: 'throw',
    favicon: cdnUrl + 'img/icon64.png',
    organizationName,
    projectName,
    customFields: {
        repoUrl,
        cdnUrl
    },
    i18n: {
        defaultLocale: 'en',
        locales: ['en', 'zh-cn'],
    },
    themeConfig: {
        image: cdnUrl + 'img/icon128.png',
        liveCodeBlock: {
            playgroundPosition: 'bottom',
        },
        colorMode: {
            defaultMode: 'light',
            disableSwitch: false,
            respectPrefersColorScheme: false,
            switchConfig: {
                darkIcon: '🌜',
                lightIcon: '☀️',
                // React inline style object
                // see https://reactjs.org/docs/dom-elements.html#style
                darkIconStyle: {
                    marginLeft: '2px',
                },
                lightIconStyle: {
                    marginLeft: '1px',
                },
            },
        },
        prism: {
            theme: require('prism-react-renderer/themes/github'),
            darkTheme: require('prism-react-renderer/themes/dracula'),
            additionalLanguages: ['java'],
        },
        algolia: {
            apiKey: 'c7c84bfcc1495156f5730309d821ba8c',
            indexName: 'sureness',
            // appId: 'GNVT7Z0UI2',
            contextualSearch: true,
        },
        announcementBar: {
            id: "github-star",
            content:
                '<font style="font-size: medium; font-weight: bolder">If you like Sureness,</font> <a target="_blank" style="font-size: medium; font-weight: bolder" rel="noopener noreferrer" href="https://github.com/dromara/sureness">give us a star on GitHub </a> <font style="font-size: medium; font-weight: bolder"> or </font><a target="_blank" style="font-size: medium; font-weight: bolder" rel="noopener noreferrer" href="https://gitee.com/dromara/sureness">Gitee! </a>⭐️',
            backgroundColor: '#7228B5',
            textColor: '#fafbfc',
            isCloseable: true, 
        },
        navbar: {
            title: ' ',
            logo: {
              alt: 'Focus on Protection of API',
              src: cdnUrl + 'img/icon128.svg',
            },
            items: [
              {
                label: 'Document',
                position: 'left',
                to: 'docs/',
              },
              {
                label: 'Blog',
                position: 'left',
                to: 'blog',
              },
              {
                label: 'Dashboard',
                position: 'left',
                href: 'https://github.com/dromara/sureness/projects/1',
              },
              {
                label: 'High Performance',
                position: 'left',
                href: 'https://github.com/tomsun28/sureness-shiro-spring-security-benchmark',
              },
              {
                label: 'Discuss',
                position: 'left',
                items: [
                  {
                    label: 'Github Discussion',
                    href: 'https://github.com/dromara/sureness/discussions',
                  },
                  {
                    label: 'Gitter Channel',
                    href: 'https://gitter.im/usthe/sureness',
                  },
                  {
                    label: 'QQ Group - 390083213',
                    href: 'https://qm.qq.com/cgi-bin/qm/qr?k=3IpzQjFOztJe464_eMBmDHfT0YTWK5Qa&jump_from=webapi',
                  },
                ],
              },
                {
                    label: 'Other',
                    position: 'left',
                    items: [
                        {
                            label: 'Design',
                            to: 'docs/design',
                        },
                        {
                            label: 'Contributing',
                            to: 'docs/contributing',
                        },
                        {
                            label: 'Sponsor',
                            to: 'docs/sponsor',
                        },
                    ],
                },
                {
                    type: 'localeDropdown',
                    position: 'right',
                },
                {
                    href: 'https://github.com/dromara/sureness',
                    position: 'right',
                    className: 'header-github-link',
                    'aria-label': 'GitHub repository',
                },
            ],
          },
        footer: {
            style: 'light',
            links: [
              {
                title: 'About Sureness',
                items: [
                  {
                    label: 'Github',
                    href: 'https://github.com/dromara/sureness',
                  },
                  {
                    label: 'Gitee',
                    href: 'https://gitee.com/dromara/sureness',
                  },
                  {
                    label: 'High Performance',
                    href: 'https://github.com/tomsun28/sureness-shiro-spring-security-benchmark',
                  },
                  {
                    label: 'Dashboard',
                    href: 'https://github.com/dromara/sureness/projects/1',
                  },
                ],
              },
              {
                title: 'Community',
                items: [
                  {
                    label: 'Dromara',
                    href: 'https://dromara.org',
                  },
                  {
                    label: 'Github Discussion',
                    href: 'https://github.com/dromara/sureness/discussions',
                  },
                  {
                    label: 'Gitter Channel',
                    href: 'https://gitter.im/usthe/sureness',
                  },
                  {
                    label: 'QQ Group - 390083213',
                    href: 'https://qm.qq.com/cgi-bin/qm/qr?k=3IpzQjFOztJe464_eMBmDHfT0YTWK5Qa&jump_from=webapi',
                  },
                ],
              },
              {
                title: 'More',
                items: [
                  {
                    label: 'Tom Blog',
                    to: 'https://blog.usthe.com',
                  },
                  {
                    label: 'USTHE',
                    href: 'https://github.com/usthe',
                  },
                  {
                    label: 'Tom',
                    href: 'https://github.com/tomsun28',
                  },
                ],
              },
            ],
            logo: {
              alt: 'Open Source Logo',
              src: cdnUrl + 'img/dromara.jpg',
              href: 'https://github.com/dromara',
            },
            copyright: `Apache License 2.0 | Copyright © ${new Date().getFullYear()}`,
          },
    },
    presets: [
        [
            '@docusaurus/preset-classic', {
                docs: {
                    sidebarPath: require.resolve('./sidebars.json'),
                    // Please change this to your repo.
                    // editUrl:'https://github.com/dromara/sureness/edit/master/home/',
                    editUrl: `${repoUrl}/edit/${branch}/home/`,
                    editLocalizedFiles: true,
                    remarkPlugins: [
                        [require('@docusaurus/remark-plugin-npm2yarn'), { sync: true }],
                    ],
                },
                blog: {
                    showReadingTime: true,
                    postsPerPage: 3,
                    feedOptions: {
                        type: 'all',
                        copyright: `Copyright © ${new Date().getFullYear()} USTHE, Inc.`,
                    },
                    // Please change this to your repo.
                    editUrl: `${repoUrl}/edit/${branch}/home/`,
                    editLocalizedFiles: true,
                },
                theme: {
                    customCss: require.resolve('./src/css/custom.css'),
                },
            },
        ],
    ],
    plugins: [
        [
            '@docusaurus/plugin-client-redirects',
            {
                fromExtensions: ['html'],
            }
        ],
        '@docusaurus/plugin-ideal-image',
        [
            '@docusaurus/plugin-pwa',
            {
                debug: false,
                offlineModeActivationStrategies: ['appInstalled', 'queryString'],
                // swRegister: false,
                swCustom: path.resolve(__dirname, 'src/sw.js'),
                pwaHead: [
                    {
                        tagName: 'link',
                        rel: 'icon',
                        href: cdnUrl + 'img/icon64.png',
                    },
                    {
                        tagName: 'link',
                        rel: 'manifest',
                        href: cdnUrl + 'manifest.json',
                    },
                    {
                        tagName: 'meta',
                        name: 'theme-color',
                        content: 'rgb(234, 90, 7)',
                    },
                    {
                        tagName: 'meta',
                        name: 'apple-mobile-web-app-capable',
                        content: 'yes',
                    },
                    {
                        tagName: 'meta',
                        name: 'apple-mobile-web-app-status-bar-style',
                        content: '#000',
                    },
                    {
                        tagName: 'link',
                        rel: 'apple-touch-icon',
                        href: cdnUrl + 'img/icon64.png',
                    },
                    {
                        tagName: 'link',
                        rel: 'mask-icon',
                        href: cdnUrl + 'img/icon64.svg',
                        color: 'rgb(234, 90, 7)',
                    },
                    {
                        tagName: 'meta',
                        name: 'msapplication-TileImage',
                        content: cdnUrl + 'img/icon64.png',
                    },
                    {
                        tagName: 'meta',
                        name: 'msapplication-TileColor',
                        content: '#000',
                    },
                ],
            },
        ],
    ],
    themes: ['@docusaurus/theme-live-codeblock'],
    stylesheets: [
        'https://fonts.googleapis.com/css2?family=IBM+Plex+Sans:wght@400;600&display=block',
        'https://fonts.googleapis.com/css2?family=IBM+Plex+Mono:wght@400;600&display=block'
    ],
    scripts: [
        'https://cdn.jsdelivr.net/gh/buttons/buttons.github.io/buttons.js'
    ]
}
