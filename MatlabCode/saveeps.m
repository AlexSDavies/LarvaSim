function saveeps(directory,filename)

if ~exist(directory, 'dir')
  mkdir(directory);
end

drawnow;

fullfilenameSVG = [directory filename '.svg'];
fullfilenamePNG = [directory filename '.png'];
fullfilenameEPS = [directory filename '.eps'];

print(gcf, '-dpng', fullfilenamePNG);

fullfilenameSVG

plot2svg(fullfilenameSVG,gcf);

% print(gcf,'-depsc',fullfilenameEPS);