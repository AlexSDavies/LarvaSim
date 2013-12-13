function saveeps(directory,filename)

if ~exist(directory, 'dir')
  mkdir(directory);
end

fullfilenameSVG = [directory filename '.svg'];
fullfilenamePNG = [directory filename '.png'];

print(gcf, '-dpng', fullfilenamePNG);

fullfilenameSVG

plot2svg(fullfilenameSVG,gcf);