const util = require("util")
const path = require("path")

const glob = require("glob")
const fs = require("graceful-fs")
const imagemin = require("imagemin")
const imageminGifsicle = require("imagemin-gifsicle")
const imageminSvgo = require("imagemin-svgo")
const makeDir = require("make-dir")
const rimraf = require("rimraf")
const sharp = require("sharp")

const writeFile = util.promisify(fs.writeFile)
const src = "static/img"
const dist = src

const optimiseImages = async () => {
  const doByExt = async (ext) => {
    let successList = []
    const files = glob.sync(`${src}/**/*.${ext}`)

    console.log(`### sharp() for .${ext} images ###\n`)

    for (const file of files) {
      await sharp(file)
        .resize({
          height: 1440,
          fit: sharp.fit.inside,
          width: 1920,
          withoutEnlargement: true,
        })
        .toFormat(ext === "jpg" ? "jpeg" : ext, {
          progressive: true,
          quality: 75,
        })
        .toFile(
          file.replace(
            new RegExp(`\/([\\w\\d-_]{0,255})\.${ext}$`),
            `/_$1.${ext}`,
          ),
        )
        .then(function () {
          successList.push(file)
          console.log(`Optimising:  ${file}`)
        })
        .catch(function (err) {
          console.log(`Fail: ${file}`, "\n", err)
        })
    }

    console.log("\n------------------------------")
    console.log("TOTAL:", successList.length)

    successList = []

    console.log(`\n### rename() for .${ext} images ###\n`)

    const rename = glob.sync(`${src}/**/_*.${ext}`)

    for (const file of rename) {
      fs.renameSync(
        file,
        file.replace(
          new RegExp(`\/_([\\w-\\d]{0,255})\.${ext}$`),
          `/$1.${ext}`,
        ),
      )
      console.log(`Renaming:  ${file}`)
      successList.push(file)
    }

    console.log("\n------------------------------")
    console.log("TOTAL:", successList.length)
    console.log("\n")
  }

  await doByExt("jpg")
  await doByExt("png")

  console.log(`### Optimise gif and svg images ###\n`)

  imagemin([`${src}/**/*.{gif,svg}`], {
    plugins: [
      imageminGifsicle({
        interlaced: true,
        optimizationLevel: 2,
      }),
      imageminSvgo({
        plugins: [
          {
            removeViewBox: false,
          },
          {
            cleanupIDs: false,
          },
          {
            removeUnknownsAndDefaults: false,
          },
          {
            convertShapeToPath: false,
          },
        ],
      }),
    ],
  }).then((files) =>
    files.forEach(async (file) => {
      let source = path.parse(file.sourcePath)
      console.log(`Optimised:  ${file.sourcePath}`)

      file.destinationPath = `${source.dir.replace(src, dist)}/${source.name}${
        source.ext
      }`

      await makeDir(path.dirname(file.destinationPath))
      await writeFile(file.destinationPath, file.data)
    }),
  )
}

if (process.env.NETLIFY === "true" || process.env.FORCE === "true") {
  optimiseImages()
  rimraf.sync("docs/__guidelines")
}
