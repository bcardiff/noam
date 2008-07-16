require 'find'

class JavaFile
	def initialize(src)
		@source_dir = src
		@file_title = src.gsub("../src/","")
		@name = File.basename(src, ".java");
	end
	attr :file_title
	attr :source_dir
	attr :name
end

class JavaPackage
	def initialize(src)
		@source_dir = src.gsub("../src/","")
		@name = @source_dir.gsub("/",".")

		@files = Array.new
		Dir.glob(src + "/*.java" ).each do |path|
			@files << JavaFile.new( path )
		end
		@files.sort! { |a,b| a.name <=> b.name }
	end
	attr :source_dir
	attr :name
	attr :files
end

packages_source_dir = Array.new

Find.find('../src/') do |path|
	if (FileTest.directory?(path) && !(path.include? ".svn") && (path != "../src/"))
		packages_source_dir << JavaPackage.new( path )
	end
end

packages_source_dir.sort! { |a,b| a.source_dir <=> b.source_dir }

packages_source_dir.each do |p|
	if (!p.files.empty?)
		puts "\\subsection{package #{p.name}}"
		puts
		p.files.each do |f|
			puts "\\fileh{#{f.file_title}}"
			puts "\\index{class!#{f.name}}"
			puts "\\lstinputlisting{#{f.source_dir}}"
			puts
		end
		puts
		puts
	end
end