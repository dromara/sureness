const path = require('path')

const organizationName = 'dromara' // Usually your GitHub org/user name.
const projectName = 'sureness' // Usually your repo name.
const branch = 'master'
const repoUrl = `https://github.com/${organizationName}/${projectName}`
const wdioLogo = 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPHN2ZyB3aWR0aD0iNjRweCIgaGVpZ2h0PSI2NHB4IiB2aWV3Qm94PSIwIDAgNjQgNjQiIHZlcnNpb249IjEuMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayI+CiAgICA8dGl0bGU+TG9nbyBSZWd1bGFyPC90aXRsZT4KICAgIDxnIGlkPSJMb2dvLVJlZ3VsYXIiIHN0cm9rZT0ibm9uZSIgc3Ryb2tlLXdpZHRoPSIxIiBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiPgogICAgICAgIDxyZWN0IGlkPSJSZWN0YW5nbGUiIGZpbGw9IiNFQTU5MDYiIHg9IjAiIHk9IjAiIHdpZHRoPSI2NCIgaGVpZ2h0PSI2NCIgcng9IjUiPjwvcmVjdD4KICAgICAgICA8cGF0aCBkPSJNOCwxNiBMOCw0OCBMNiw0OCBMNiwxNiBMOCwxNiBaIE00MywxNiBDNTEuODM2NTU2LDE2IDU5LDIzLjE2MzQ0NCA1OSwzMiBDNTksNDAuODM2NTU2IDUxLjgzNjU1Niw0OCA0Myw0OCBDMzQuMTYzNDQ0LDQ4IDI3LDQwLjgzNjU1NiAyNywzMiBDMjcsMjMuMTYzNDQ0IDM0LjE2MzQ0NCwxNiA0MywxNiBaIE0yNywxNiBMMTQuMTA2LDQ3Ljk5OTIwNzggTDExLjk5OSw0Ny45OTkyMDc4IEwyNC44OTQsMTYgTDI3LDE2IFogTTQzLDE4IEMzNS4yNjgwMTM1LDE4IDI5LDI0LjI2ODAxMzUgMjksMzIgQzI5LDM5LjczMTk4NjUgMzUuMjY4MDEzNSw0NiA0Myw0NiBDNTAuNzMxOTg2NSw0NiA1NywzOS43MzE5ODY1IDU3LDMyIEM1NywyNC4yNjgwMTM1IDUwLjczMTk4NjUsMTggNDMsMTggWiIgaWQ9IkNvbWJpbmVkLVNoYXBlIiBmaWxsPSIjRkZGRkZGIj48L3BhdGg+CiAgICA8L2c+Cjwvc3ZnPg=='

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
    themeConfig: {
        image: 'img/icon64128.png',
        metadatas: [{ name: 'twitter:card', content: 'summary' }],
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
            darkTheme: require('prism-react-renderer/themes/dracula')
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
            ],
          },
          footer: {
            style: 'light',
            links: [
              {
                title: 'About Sureness',
                items: [
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
                    label: 'Github',
                    href: 'https://github.com/dromara/sureness',
                  },
                  {
                    label: 'Gitee',
                    href: 'https://gitee.com/dromara/sureness',
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
                // docs: {
                //     sidebarPath: require.resolve('./sidebars.js'),
                //     // Please change this to your repo.
                //     editUrl:`${repoUrl}/edit/${branch}/website/`,
                //     remarkPlugins: [
                //         [require('@docusaurus/remark-plugin-npm2yarn'), { sync: true }],
                //     ],
                // },
                blog: {
                    showReadingTime: true,
                    postsPerPage: 3,
                    // Please change this to your repo.
                    editUrl: `${repoUrl}/edit/${branch}/website/blog/`,
                },
                theme: {
                    customCss: require.resolve('./src/css/custom.css'),
                },
                // pages: {
                //     remarkPlugins: [require('@docusaurus/remark-plugin-npm2yarn')],
                // },
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
        // [
        //     '@docusaurus/plugin-content-docs',
        //     {
        //         id: 'community',
        //         path: 'community',
        //         editUrl: `https://github.com/${organizationName}/${projectName}/edit/${branch}/website/`,
        //         routeBasePath: 'community',
        //         sidebarPath: require.resolve('./sidebarsCommunity.js')
        //     },
        // ],
        // '@docusaurus/plugin-ideal-image',
        // [
        //     '@docusaurus/plugin-pwa',
        //     {
        //         debug: false,
        //         offlineModeActivationStrategies: ['appInstalled', 'queryString'],
        //         // swRegister: false,
        //         swCustom: path.resolve(__dirname, 'src/sw.js'),
        //         pwaHead: [
        //             {
        //                 tagName: 'link',
        //                 rel: 'icon',
        //                 href: 'img/logo-webdriver-io.png',
        //             },
        //             // {
        //             //     tagName: 'link',
        //             //     rel: 'manifest',
        //             //     href: '/manifest.json',
        //             // },
        //             {
        //                 tagName: 'meta',
        //                 name: 'theme-color',
        //                 content: 'rgb(234, 90, 7)',
        //             },
        //             {
        //                 tagName: 'meta',
        //                 name: 'apple-mobile-web-app-capable',
        //                 content: 'yes',
        //             },
        //             {
        //                 tagName: 'meta',
        //                 name: 'apple-mobile-web-app-status-bar-style',
        //                 content: '#000',
        //             },
        //             {
        //                 tagName: 'link',
        //                 rel: 'apple-touch-icon',
        //                 href: 'img/logo-webdriver-io.png',
        //             },
        //             {
        //                 tagName: 'link',
        //                 rel: 'mask-icon',
        //                 href: 'img/logo-webdriver-io.svg',
        //                 color: 'rgb(234, 90, 7)',
        //             },
        //             {
        //                 tagName: 'meta',
        //                 name: 'msapplication-TileImage',
        //                 content: 'img/logo-webdriver-io.png',
        //             },
        //             {
        //                 tagName: 'meta',
        //                 name: 'msapplication-TileColor',
        //                 content: '#000',
        //             },
        //         ],
        //     },
        // ],
    ],
    themes: ['@saucelabs/theme-github-codeblock'],
    stylesheets: [
        'https://fonts.googleapis.com/css2?family=IBM+Plex+Sans:wght@400;600&display=block',
        'https://fonts.googleapis.com/css2?family=IBM+Plex+Mono:wght@400;600&display=block'
    ],
    scripts: [
        'https://buttons.github.io/buttons.js'
    ]
}
