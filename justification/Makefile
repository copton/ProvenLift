.PHONY: all clean view

DOCS=justification

all: $(foreach DOC, $(DOCS), $(DOC).pdf)

%.pdf: %.tex
	pdflatex $< && pdflatex $<

clean:
	rm -f $(foreach NAME, $(DOCS), $(foreach EXT, pdf aux bbl log dvi blg tex.backup, $(NAME).$(EXT)))
