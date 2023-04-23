---
layout: default
title: Test Jekyll Webpage build Locally 
nav_exclude: true
---


# Testing Webpage Locally
1. [Follow specific Ruby Set Up instrictions for M1 Mac](https://www.moncefbelyamani.com/how-to-install-xcode-homebrew-git-rvm-ruby-on-mac/#start-here-if-you-choose-the-long-and-manual-route)
2. Install Ruby, Bundler, ...
3. Open Project & Terminal in IDE
4. Create Gemfile with `bundle init`
5. Install GH Pages gem with `gem install github-pages`
6. Add to project `bundle add github-pages` (Ignore if gemfile already has this gem added)
7. Install webrick gem `gem install webrick`
8. Add to project `bundle add webrick` (Ignore if gemfile already has this gem added)
9. Add jekyll theme `gem install just-the-docs`
10. Add to project `bundle add just-the-docs` (Ignore if gemfile already has this gem added)
11. Add Theme to `_config.yaml`: Uncomment `# theme: just-the-docs`   
     **Note**: Make sure to comment this line back before pushing changes to git

8. Install Dependencies `bundle install`
9. Run Jekyll project `bundle exec jekyll serve`


<br>
 **Note:** All .md must have at least the following on the very top of the file to display on local serve:

```
---
layout: <layout>
title: <title>
---
```

<br>
**Note**
