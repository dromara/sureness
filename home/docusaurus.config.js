const path = require('path')

const organizationName = 'dromara' // Usually your GitHub org/user name.
const projectName = 'sureness' // Usually your repo name.
const branch = 'master'
const repoUrl = `https://github.com/${organizationName}/${projectName}`

module.exports = {
    title: 'Sureness',
    tagline: 'Focusing on Protection of REST API',
    url: 'https://su.usthe.com',
    baseUrl: '/',
    onBrokenLinks: 'throw',
    onBrokenMarkdownLinks: 'throw',
    favicon: 'img/icon64.png',
    organizationName,
    projectName,
    customFields: {
        repoUrl
    },
    i18n: {
        defaultLocale: 'en',
        locales: ['en', 'zh-cn'],
    },
    themeConfig: {
        image: 'img/icon64128.png',
        liveCodeBlock: {
            playgroundPosition: 'bottom',
        },
        colorMode: {
            defaultMode: 'light',
            disableSwitch: false,
            respectPrefersColorScheme: true,
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
            apiKey: '31a1c9b96ae0df998aa655296167448f',
            indexName: 'sureness',
            appId: 'GNVT7Z0UI2'
        },
        announcementBar: {
            id: "github-star",
            content:
                '<font style="font-size: medium; font-weight: bolder">If you like Sureness,</font> <a target="_blank" style="font-size: medium; font-weight: bolder" rel="noopener noreferrer" href="https://github.com/dromara/sureness">give us a star on GitHub! </a>⭐️',
            backgroundColor: '#7228B5',
            textColor: '#fafbfc',
            isCloseable: true, 
        },
        navbar: {
            title: ' ',
            logo: {
              alt: 'Focusing on Protection of REST API',
              src: 'img/icon128.svg',
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
                label: 'Community',
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
                    label: 'QQ Group',
                    href: 'https://qm.qq.com/cgi-bin/qm/qr?k=3IpzQjFOztJe464_eMBmDHfT0YTWK5Qa&jump_from=webapi',
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
                    label: 'Github Discussion',
                    href: 'https://github.com/dromara/sureness/discussions',
                  },
                  {
                    label: 'Gitter Channel',
                    href: 'https://gitter.im/usthe/sureness',
                  },
                  {
                    label: 'QQ Group',
                    href: 'https://qm.qq.com/cgi-bin/qm/qr?k=3IpzQjFOztJe464_eMBmDHfT0YTWK5Qa&jump_from=webapi',
                  },
                ],
              },
              {
                title: 'More',
                items: [
                  {
                    label: 'Blog',
                    to: '/blog',
                  },
                  {
                    label: 'USTHE',
                    href: 'https://github.com/usthe',
                  },
                ],
              },
            ],
            logo: {
              alt: 'Open Source Logo',
              src: 'img/icon128.svg',
              href: 'https://github.com/dromara/sureness',
            },
            copyright: `Apache License, Version 2.0 | Copyright © ${new Date().getFullYear()} USTHE.`,
          },
    },
    presets: [
        [
            '@docusaurus/preset-classic', {
                docs: {
                    sidebarPath: require.resolve('./sidebars.json'),
                    // Please change this to your repo.
                    editUrl:`${repoUrl}/edit/${branch}/home/`,
                    remarkPlugins: [
                        [require('@docusaurus/remark-plugin-npm2yarn'), { sync: true }],
                    ],
                },
                blog: {
                    showReadingTime: true,
                    postsPerPage: 3,
                    feedOptions: {
                        type: 'all',
                        copyright: `Copyright © ${new Date().getFullYear()} Facebook, Inc.`,
                    },
                    // Please change this to your repo.
                    editUrl: `${repoUrl}/edit/${branch}/home/`,
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
                        href: 'img/icon64.png',
                    },
                    {
                        tagName: 'link',
                        rel: 'manifest',
                        href: '/manifest.json',
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
                        href: 'img/icon64.png',
                    },
                    {
                        tagName: 'link',
                        rel: 'mask-icon',
                        href: 'img/icon64.svg',
                        color: 'rgb(234, 90, 7)',
                    },
                    {
                        tagName: 'meta',
                        name: 'msapplication-TileImage',
                        content: 'img/icon64.png',
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
        'https://buttons.github.io/buttons.js'
    ]
}
