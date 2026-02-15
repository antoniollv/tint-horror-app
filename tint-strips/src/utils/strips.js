export const getChapters = (data) => Array.isArray(data?.strips) ? data.strips : [];

export const getChapterVignettes = (data, chapterIndex) => {
  const chapters = getChapters(data);
  const chapter = chapters[chapterIndex];
  if (!chapter || !Array.isArray(chapter.vignettes)) {
    return [];
  }
  return chapter.vignettes;
};
