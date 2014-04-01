
subplot(1,2,1);
hold on;
plot([0,20],[1,-1],'-r','linewidth',2);
plot([0,20],[0,0],'-k');
set(gca,'YTick',[]);
xlabel('Time (s)'); ylabel('Kernel value');
title('Run termination');

subplot(1,2,2);
hold on;
plot([0,4,5],[0,0,10],'-r','linewidth',2);
plot([0,5],[0,0],'-k');
set(gca,'YTick',[]);
xlabel('Time (s)'); ylabel('Kernel value');
title('Cast termination');

set(gcf,'Position',[280 278 814 216]);

saveeps('D:\Dropbox\Uni\PhD\ReviewFigures\Misc\','Kernels');