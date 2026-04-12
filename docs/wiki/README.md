# Wiki Source Files

This folder contains markdown pages prepared for the GitHub Wiki for Item Display Control.

## Files

- `Home.md`
- `Installation-and-Compatibility.md`
- `User-Guide.md`
- `Development-and-Release.md`
- `Troubleshooting.md`
- `_Sidebar.md`

## Publish to GitHub Wiki

Option 1: Web UI copy-paste

1. Open the repository Wiki tab
2. Create pages with the same names
3. Paste content from files in this folder

Option 2: Push to wiki git repository

```bash
git clone https://github.com/kizio806/item-display-control.wiki.git /tmp/item-display-control-wiki
cp docs/wiki/*.md /tmp/item-display-control-wiki/
cd /tmp/item-display-control-wiki
git add .
git commit -m "docs(wiki): initialize project wiki pages"
git push
```
