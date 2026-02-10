# tint-horror-app

[EN](README.md) | [ES](README_es.md)

React web app to view comic strips in the browser. The app loads a YAML configuration file and renders the panels with their speech bubbles. This README is designed to help you run, deploy, and release the project smoothly.

## Structure

- React app: [tint-strips/](tint-strips)
- Release configuration: [.releaserc.json](.releaserc.json)
- Publish workflow: [.github/workflows/publish.yml](.github/workflows/publish.yml)
- Changelog: [CHANGELOG.md](CHANGELOG.md)

## Requirements

- Node.js 22+
- npm 9+

## Local development

From the app folder, the usual steps are:

| Step | Description | Command |
| --- | --- | --- |
| 1 | Install dependencies. | `npm install` |
| 2 | Start development server. | `npm run dev` |
| 3 | Build for production. | `npm run build` |

### Available scripts (Vite)

Defined in [tint-strips/package.json](tint-strips/package.json):

| Script | Description | Command |
| --- | --- | --- |
| `dev` | Development server. | `npm run dev` |
| `start` | Alias for `dev`. | `npm start` |
| `build` | Production build. | `npm run build` |
| `preview` | Local preview of the build. | `npm run preview` |

## Data configuration

The app loads a YAML file from:

- `VITE_YAML_CONFIG_PATH` if defined
- `/comics.yml` if no variable is set

The file must be in `public/` and is copied to the build.

Images must live in `tint-strips/public/imgs/` so they resolve under `/imgs/`.

Compatible alternative variables:

- `REACT_APP_YAML_CONFIG_PATH`
- `REACT_APP_IMAGE_PATH`
- `REACT_APP_TRANSLATION_API_URL`
- `REACT_APP_TRANSLATION_API_KEY`

## S3 deployment (SPA)

The app is an SPA without client-side routes, so S3 is enough.

Quick checklist:

- Build: `npm run build`
- Upload **the contents** of `tint-strips/build/` to S3
- Static website hosting:
  - Index document: `index.html`
  - Error document: `index.html` (future route compatibility)

In [tint-strips/package.json](tint-strips/package.json) we use `"homepage": "."` for relative paths.

### Bucket public policy (if not using CloudFront)

If you deploy with the provided workflows, the bucket policy is applied automatically.
Remember to enable public read-only access to the bucket. Example policy:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "PublicReadGetObject",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::TU_BUCKET/*"
    }
  ]
}
```

### Optional: CloudFront

If you want HTTPS, better caching, and a custom domain:

- Create a distribution with the S3 bucket as origin
- Set `index.html` as the Default Root Object
- (If routes are added later) add a Custom Error Response 404 → `/index.html` (200)
- Invalidate cache on new releases

## Versioning and releases (semantic-release)

The automated flow is prepared to:

- Create a tag on `main` with format `vX.Y.Z`
- Update `CHANGELOG.md`
- Create a GitHub Release
- Bump the version on `develop` (package.json + package-lock.json)

### Configuration

- Main configuration: [.releaserc.json](.releaserc.json)
- Workflow on `main`: [.github/workflows/publish.yml](.github/workflows/publish.yml)

### Commit conventions (Conventional Commits)

The commit type determines the release version:

- `feat:` → **minor**
- `fix:` → **patch**
- `perf:` → **patch**
- `refactor:` → **patch** (if behavior changes)
- `docs:`, `chore:`, `test:`, `build:`, `ci:` → **no release**

For a **major** release, add `BREAKING CHANGE:` in the commit body.

Valid examples:

- `feat: add chapter selector`
- `fix: correct comics.yml loading`
- `refactor: simplify strip loading`

Note: for breaking changes, add `BREAKING CHANGE:` in the body.

---

## Current project status

Brief summary of the code status and items to review.

### What is working

- React app with panel navigation.
- Dynamic speech bubbles and auto-translation.
- YAML configuration loading.
- Development and production Docker setup.

### Pending or to review

- Assets (comics.yml and SVG images) must exist in public/.
- Translation service must be externally available.
- Tests are not defined yet.

### Key files

- Main app: [tint-strips/src/App.jsx](tint-strips/src/App.jsx)
- Bubbles: [tint-strips/src/components/ComicPanel.jsx](tint-strips/src/components/ComicPanel.jsx)
- YAML config: [tint-strips/src/components/loadConfigStrips.js](tint-strips/src/components/loadConfigStrips.js)
- Translation: [tint-strips/src/utils/translate.js](tint-strips/src/utils/translate.js)
- Docker: [tint-strips/Dockerfile.dev](tint-strips/Dockerfile.dev) and [tint-strips/Dockerfile.pro](tint-strips/Dockerfile.pro)

---

## License

This project is published as open source under:

- Code: MIT. See [LICENSE](LICENSE).
- Images: Creative Commons Attribution 4.0 (CC BY 4.0). See [LICENSE-IMAGES](LICENSE-IMAGES).
